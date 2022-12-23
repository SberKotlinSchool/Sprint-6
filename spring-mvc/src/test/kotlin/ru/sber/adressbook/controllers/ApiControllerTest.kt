package ru.sber.adressbook.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class ApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookService: BookService

    val mapper = jacksonObjectMapper()

    fun getUrl(path: String) : String = "http://localhost:${port}/${path}"

    @Test
    fun addAddress() {

        val expected = DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        )

        val response = restTemplate.postForEntity(
            getUrl("/api/add"),
            HttpEntity(expected, HttpHeaders()),
            DataBook::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertTrue(response.body!! == expected)
        bookService.clear()
    }

    @Test
    fun getBooks() {
        val result = restTemplate.exchange(
            getUrl("/api/list"),
            HttpMethod.GET,
            HttpEntity(""),
            String::class.java
        )

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(mapper.writeValueAsString(bookService.getListBooks()), result.body)
    }

    @Test
    fun viewBook() {
        val expected = DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        )

        bookService.clear()
        restTemplate.postForEntity(
            getUrl("/api/add"),
            HttpEntity(expected, HttpHeaders()),
            DataBook::class.java
        )

        val result = restTemplate.exchange(
            getUrl("/api/0/view"),
            HttpMethod.GET,
            HttpEntity(""),
            String::class.java
        )

        assertEquals(HttpStatus.FOUND, result.statusCode)
        assertEquals(mapper.writeValueAsString(expected), result.body)
    }

    @Test
    fun editBook() {
        val origin = DataBook(
            "Иван",
            "Иванов",
            "ivan666@mail.ru",
            "79876543211",
            "РФ, Москва, ул. Пушкина"
        )

        restTemplate.postForEntity(
            getUrl("/api/add"),
            HttpEntity(origin, HttpHeaders()),
            DataBook::class.java
        )

        val update = DataBook(
            "Иван",
            "Иванов",
            "ivan999999@mail.ru",
            "7999999999",
            "РФ, Питер"
        )

        val result = restTemplate.exchange(
            getUrl("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(update, HttpHeaders()),
            DataBook::class.java
        )

        assertEquals(update, result.body)
        assertEquals(HttpStatus.OK, result.statusCode)

        bookService.clear()
    }

    @Test
    fun removeBook() {
        val expected = DataBook(
            "Иван",
            "Иванов",
            "ivan999999@mail.ru",
            "7999999999",
            "РФ, Питер"
        )

        restTemplate.postForEntity(
            getUrl("/api/add"),
            HttpEntity(expected, HttpHeaders()),
            DataBook::class.java
        )

        val result = restTemplate.exchange(
            getUrl("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(""),
            String::class.java
        )

        assertEquals(HttpStatus.FOUND, result.statusCode)
        assertEquals(mapper.writeValueAsString(expected), result.body)
        bookService.clear()
    }

}