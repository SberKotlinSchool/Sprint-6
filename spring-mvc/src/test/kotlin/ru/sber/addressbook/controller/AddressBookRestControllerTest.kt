package ru.sber.addressbook.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import ru.sber.addressbook.data.Contact
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookRestControllerTest{

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String) = "http://localhost:$port/$path"

    private fun headersWithCookie() = HttpHeaders()
        .also {  it.add("Cookie",
            "auth=${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss"))}")}

    @Test
    fun getAddressBookTest() {
        val response = restTemplate.exchange(url("api/list"), HttpMethod.GET,
            HttpEntity(null, headersWithCookie()),
            Map::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, (response.body as Map<Long, Contact>).size)
    }

    @Test
    fun createTest() {
        val result = restTemplate.postForObject(url("api/add"),  HttpEntity(Contact(
            "Сидоров", "Александр", "Федорович",
            LocalDate.parse("10.02.2001", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            "+7 111 111-11-11", "sidorova@mail.ru"
        ), headersWithCookie()), Contact::class.java)
        assertNotNull(result)
        assertEquals("Сидоров", (result as Contact).lastName)
    }

    @Test
    fun readTest() {
        val response  = restTemplate.restTemplate.exchange(url("api/2/view"), HttpMethod.GET,
            HttpEntity(null, headersWithCookie()),
            Contact::class.java)
        assertEquals(HttpStatus.FOUND, response.statusCode)
        assertEquals("Федорович", (response.body as Contact).middleName)
    }

    @Test
    fun editTest() {
        val response = restTemplate.restTemplate.exchange(url("api/2/edit"), HttpMethod.PUT,
            HttpEntity(Contact(
                "Попов", "Петр", "Федорович",
                LocalDate.parse("01.01.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "+7 333 333-33-33", "popovp@mail.ru"), headersWithCookie()),
            Contact::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("+7 333 333-33-33", (response.body as Contact).phoneNumber)
    }

    @Test
    fun deleteTest() {
        val response = restTemplate.restTemplate.exchange(
            url("api/1/delete"), HttpMethod.DELETE,
            HttpEntity(null, headersWithCookie()),
            Unit::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

}