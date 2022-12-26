package ru.sber.addressbook.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import ru.sber.addressbook.model.Person
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApiControllerTest {

    val headers = HttpEntity(
        null,
        HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") })

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun list() {
        val actualResponse = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            headers,
            Array<Person>::class.java
        )

        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(2, actualResponse.body!!.size)
    }

    @Test
    fun add() {
        val actualResponse = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            headers,
            Long::class.java
        )

        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
    }

    @Test
    fun getById() {
        val actualResponse =
            restTemplate.exchange(
                url("/api/1/view"),
                HttpMethod.GET,
                headers,
                Person::class.java
            )

        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(1L, actualResponse.body!!.id)
    }

    @Test
    fun update() {
        val actualResponse =
            restTemplate.exchange(
                url("/api/edit"),
                HttpMethod.POST,
                headers,
                Person::class.java
            )

        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
    }

    @Test
    fun delete() {
        val actualResponse =
            restTemplate.exchange(
                url("/api/0/delete"),
                HttpMethod.GET,
                headers,
                String::class.java
            )

        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals("Ok", actualResponse.body)

    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }
}