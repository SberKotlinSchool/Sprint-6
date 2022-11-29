package ru.sbrf.school.kotlin.springmvc.controller

import org.junit.jupiter.api.Assertions.*
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
import ru.sbrf.school.kotlin.springmvc.entity.Person
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class ApiControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    fun headers(): HttpHeaders = HttpHeaders().also { it.add("Cookie", "auth=${LocalDate.now()}") }
    private fun url(path: String) = "http://localhost:${port}/${path}"

    @Test
    fun list() {
        restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(
                null,
                headers()
            ), Array<Person>::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals(2, this.body!!.size)
            assertEquals(this.body!!.first().name, "Vasiliy")
            assertEquals(this.body!!.first().phone, "55-12-09")
        }
    }

    @Test
    fun view() {
        restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, headers()),
            Person::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals("Vasiliy", this.body!!.name)
            assertEquals("55-12-09", this.body!!.phone)
        }
    }

    @Test
    fun add() {
        restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(Person(name = "Uasya", phone = "88 888 888"), headers()),
            Person::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun edit() {
        restTemplate.exchange(
            url("api/1/edit"),
            HttpMethod.PUT,
            HttpEntity(Person(name = "Uasya", phone = "88 888 888"), headers()),
            Person::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals("Uasya", this.body!!.name)
            assertEquals("88 888 888", this.body!!.phone)
        }
    }

    @Test
    fun delete() {
        restTemplate.exchange(
            url("api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers()),
            Unit::class.java
        ).apply {
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

}