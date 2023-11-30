package ru.sber.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import ru.sber.database.DbService
import ru.sber.dto.AddressDto
import ru.sber.dto.QueryDto
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var dbService: DbService

    @LocalServerPort
    private var port: Int = 0

    val apiUrl = { url: String -> "http://localhost:${port}/${url}" }

    @AfterEach
    fun clearDb() {
        dbService.deleteAll()
    }

    @Test
    fun checkList() {
        val createdAddress = dbService.createAddress(AddressDto("улица Шкловского"))

        val res = restTemplate.exchange(
                apiUrl.invoke("api/list"),
                HttpMethod.GET,
                HttpEntity(null, getAuthCookie()),
                typeRef<List<AddressDto>>())

        assertEquals(1, res.body?.size)
        assertEquals(listOf(createdAddress), res.body)
    }

    @Test
    fun checkListWithFilter() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Пушкина"))
        val createdAddress2 = dbService.createAddress(AddressDto("улица Достоесвкого"))
        val createdAddress3 = dbService.createAddress(AddressDto("проспект Гоголя"))


        val res = restTemplate.exchange(
                apiUrl.invoke("api/list"),
                HttpMethod.POST,
                HttpEntity(QueryDto(byName = "улица"), getAuthCookie()),
                typeRef<List<AddressDto>>())
        assertEquals(2, res.body?.size)
        assertEquals(listOf(createdAddress1, createdAddress2), res.body)
    }


    @Test
    fun checkAdd() {

        val res = restTemplate.postForEntity(
                apiUrl.invoke("api/add"),
                HttpEntity(AddressDto(name = "улица Шкловского"), getAuthCookie()),
                ResponseEntity::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)

        assertEquals(listOf("улица Шкловского"), dbService.findAll().map { it.name })
    }

    @Test
    fun checkView() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Молодежная"))
        val createdAddress2 = dbService.createAddress(AddressDto("улица Школьная"))
        val res = restTemplate.exchange(
                apiUrl.invoke("api/1/view"),
                HttpMethod.GET,
                HttpEntity(null, getAuthCookie()),
                typeRef<AddressDto>())

        assertEquals(createdAddress1, res.body)
    }

    @Test
    fun testEdit() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Центральная"))

        val res = restTemplate.exchange(
                apiUrl.invoke("api/1/edit"),
                HttpMethod.PUT,
                HttpEntity(AddressDto(name = "улица Шкловского"), getAuthCookie()),
                typeRef<Boolean>())

        assertEquals("улица Шкловского", dbService.findById(createdAddress1.id!!)?.name)
    }

    @Test
    fun testDelete() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Центральная"))

        val res = restTemplate.exchange(
                apiUrl.invoke("api/1/delete"),
                HttpMethod.DELETE,
                HttpEntity(null, getAuthCookie()),
                typeRef<Boolean>())

        assertEquals(listOf<AddressDto>(), dbService.findAll())
    }


    private fun getAuthCookie(): HttpHeaders {
        return HttpHeaders().apply {
            add("Cookie", "auth=${LocalDateTime.now()}")
        }
    }

    private inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

}