package ru.sber.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.sber.database.DbService
import ru.sber.dto.AddressDto
import ru.sber.servlet.LoginServlet
import java.time.LocalDateTime
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var dbService: DbService

    @AfterEach
    fun clearDb() {
        dbService.deleteAll()
    }

    @Test
    fun testList() {
        val createdAddress = dbService.createAddress(AddressDto("улица Шкловского"))

        val res = callForResult(HttpMethod.GET, "/app/list")

        assertTrue(res.startsWith("<!DOCTYPE html>"))
        assertTrue(res.contains("<td>улица Шкловского</td>"))
    }


    @Test
    fun checkListWithFilter() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Пушкина"))
        val createdAddress2 = dbService.createAddress(AddressDto("улица Достоесвкого"))
        val createdAddress3 = dbService.createAddress(AddressDto("проспект Гоголя"))

        val res = callForResult(HttpMethod.POST, "/app/list", "byName=улица")

        assertTrue(res.contains("<td>улица Пушкина</td>"))
        assertTrue(res.contains("<td>улица Достоесвкого</td>"))
        assertFalse(res.contains("<td>проспект Гоголя</td>"))
    }

    @Test
    fun checkAdd() {

        val res = callForResult(HttpMethod.POST, "/app/add", "name=улица Шкловского")

        Assertions.assertEquals(listOf("улица Шкловского"), dbService.findAll().map { it.name })
    }

    @Test
    fun checkView() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Молодежная"))
        val createdAddress2 = dbService.createAddress(AddressDto("улица Школьная"))

        val res = callForResult(HttpMethod.GET, "/app/1/view")

        assertTrue(res.contains("<td>улица Молодежная</td>"))
        assertFalse(res.contains("<td>проспект Школьная</td>"))
    }


    @Test
    fun testEdit() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Центральная"))

        val res = callForResult(HttpMethod.POST, "/app/1/edit", "name=улица Шкловского")

        Assertions.assertEquals("улица Шкловского", dbService.findById(createdAddress1.id!!)?.name)
    }

    @Test
    fun testDelete() {
        val createdAddress1 = dbService.createAddress(AddressDto("улица Центральная"))

        val res = callForResult(HttpMethod.POST, "/app/1/delete")

        Assertions.assertEquals(listOf<AddressDto>(), dbService.findAll())
    }

    protected fun callForResult(
            method: HttpMethod,
            url: String,
            content: String? = null
    ): String {
        val reqBuilder = MockMvcRequestBuilders
                .request(method, url)

        if (content != null) {
            reqBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            reqBuilder.content(content)
        }

        reqBuilder.cookie(Cookie(LoginServlet.AUTH_HEADER, LocalDateTime.now().minusDays(1).toString()))
        reqBuilder.accept(MediaType.APPLICATION_JSON)

        return mockMvc.perform(reqBuilder)
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString

    }

}