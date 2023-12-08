package com.example.adresbook.controller

import com.example.adresbook.AddressBookRepository
import com.example.adresbook.model.BookRecord
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressBookRestControllerTest {
    @Autowired
    private lateinit var template: TestRestTemplate

    @Autowired
    private lateinit var bookRepository: AddressBookRepository

    private val cookie = HttpHeaders().set("Cookie", "auth=${LocalDateTime.now()}")

    @Test
    fun add() {
        val headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        val result = template.postForEntity(
            "http://localhost:8080/api/v1/add",
            HttpEntity(BookRecord(4, "Проспект Ленина 4", "4"), headers),
            Boolean::class.java
        )
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun list() {
        val headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        val result = template.exchange(
            "http://localhost:8080/api/v1/list",
            HttpMethod.GET,
            HttpEntity(null, headers),
            List::class.java
        )
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(result.body?.size, bookRepository.pseudoDataBase.size)
    }

    @Test
    fun view() {
        val headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        val result = template.exchange(
            "http://localhost:8080/api/v1/1/view",
            HttpMethod.GET,
            HttpEntity(null, headers),
            BookRecord::class.java
        )
        assertEquals(HttpStatus.OK, result.statusCode)
        assertNotNull(result.body)
    }

    @Test
    fun edit() {
        val bookRecord = BookRecord(4, "Проспект Ленина 4", "4")
        val headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        val result = template.exchange(
            "http://localhost:8080/api/v1/1/edit",
            HttpMethod.POST,
            HttpEntity(bookRecord, headers),
            BookRecord::class.java
        )
        assertEquals(HttpStatus.OK, result.statusCode)
        assertNotNull(result.body)
        assertEquals(bookRecord, result.body)
    }

    @Test
    fun delete() {
        val headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        val result = template.exchange(
            "http://localhost:8080/api/v1/3",
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            Boolean::class.java
        )
        assertEquals(HttpStatus.OK, result.statusCode)
        assertNotNull(result.body)
        assertEquals(true, result.body)
    }
}