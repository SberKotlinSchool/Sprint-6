package ru.sber

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import ru.sber.model.Person
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookRestControllerTests {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    fun addHeader(): HttpHeaders = HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }

    fun addPerson(headers: HttpHeaders) {
        val person = Person(1, "ФИО", "Адрес", "911", "email@gmail.com")
        restTemplate.postForEntity(url("api/add"), HttpEntity(person, headers), Person::class.java)
    }

    @Test
    fun addPersonTest() {
        val headers = addHeader()
        addPerson(headers)
        val person = Person(null, "ФИО", "Адрес", "912", "email1@gmail.com")
        val resp = restTemplate.postForEntity(url("api/add"), HttpEntity(person, headers), Person::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun getListTest() {
        val headers = addHeader()
        addPerson(headers)
        val response =
            restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Person>::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun viewTest() {
        val headers = addHeader()
        addPerson(headers)
        val resp =
            restTemplate.exchange(url("api/1/view"), HttpMethod.GET, HttpEntity(null, headers), Person::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals("ФИО", resp.body?.fio)
    }

    @Test
    fun editPersonTest() {
        val headers = addHeader()
        addPerson(headers)
        val person = Person(1, "Иванов", "Морская", "913", "emailEdit@gmail.com")
        val resp =
            restTemplate.exchange(url("api/1/edit"), HttpMethod.PUT, HttpEntity(person, headers), String::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)

        val response =
            restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Person>::class.java)
        assertEquals("Иванов", response.body?.get(0)!!.fio)
        assertEquals("Морская", response.body?.get(0)!!.address)
        assertEquals("913", response.body?.get(0)!!.phone)
        assertEquals("emailEdit@gmail.com", response.body?.get(0)!!.email)
    }

    @Test
    fun deletePersonTest() {
        val headers = addHeader()
        addPerson(headers)
        val resp =
            restTemplate.exchange(url("api/1/delete"), HttpMethod.DELETE, HttpEntity(null, headers), String::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }
}