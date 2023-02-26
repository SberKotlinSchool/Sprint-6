package ru.sber.contoller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import ru.sber.model.AddressModel
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApplicationTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val addressModel = AddressModel("mockAddress")

    private fun cookieHeader() = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
    }
    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @Test
    fun testAdd() {
        val res = restTemplate.postForEntity(url("api/add"), HttpEntity(addressModel, cookieHeader()), ResponseEntity::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)
    }

    @Test
    fun testEdit() {
        val res = restTemplate.exchange(url("api/0/edit"), HttpMethod.PUT, HttpEntity(AddressModel("stub"), cookieHeader()), ResponseEntity::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)
    }

    @Test
    fun testList() {
        restTemplate.postForEntity(url("api/add"), HttpEntity(addressModel, cookieHeader()), ResponseEntity::class.java)
        val res = restTemplate.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity(null, cookieHeader()),
            Map::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)
        assertNotNull(res)
    }

    @Test
    fun testView() {
        restTemplate.postForEntity(url("api/add"), HttpEntity(addressModel, cookieHeader()), ResponseEntity::class.java)
        val res = restTemplate.exchange(
            url("api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, cookieHeader()),
            AddressModel::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)
        assertNotNull(res.body)
    }

    @Test
    fun testDelete() {
        restTemplate.postForEntity(url("api/add"), HttpEntity(addressModel, cookieHeader()), ResponseEntity::class.java)
        val res = restTemplate.exchange(
            url("api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, cookieHeader()),
            ResponseEntity::class.java)
        assertEquals(HttpStatus.OK, res.statusCode)
    }
}