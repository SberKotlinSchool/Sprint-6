package ru.sber.spring.mvc.controller

import org.junit.jupiter.api.Assertions.*
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
import ru.sber.spring.mvc.model.Book
import ru.sber.spring.mvc.model.ErrorMessage
import ru.sber.spring.mvc.service.BookService
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class BookRestControllerTest {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var bookService: BookService;

    private fun getHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        val expDate = LocalDateTime.now().plusMinutes(5).toString()
        httpHeaders.add("Cookie", "auth=${expDate}")
        return httpHeaders
    }

    @Test
    fun add() {
        val book = Book(name = "new book", author = "Pushkin")
        val resp =
            restTemplate.postForEntity(
                "http://localhost:${port}/rest/book/add", HttpEntity(book, getHeaders()), Book::class.java
            )

        assertNotNull(resp.body?.id)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun list() {
        bookService.add(Book(name = "new book 1", author = "Pushkin"))
        bookService.add(Book(name = "new book 2", author = "Pushkin"))

        val resp = restTemplate.exchange(
            "http://localhost:${port}/rest/book/list",
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            List::class.java
        )

        assertEquals(2, resp.body?.size)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun `view book is not found`() {

        val id = 1
        val resp = restTemplate.exchange(
            "http://localhost:${port}/rest/book/${id}/view",
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            ErrorMessage::class.java
        )

        assertEquals("Book is not found", resp.body?.message)
        assertEquals(HttpStatus.BAD_REQUEST, resp.statusCode)
    }

    @Test
    fun view() {
        bookService.add(Book(name = "new book 1", author = "Pushkin"))

        val id = 1
        val resp = restTemplate.exchange(
            "http://localhost:${port}/rest/book/${id}/view",
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            Book::class.java
        )

        assertEquals(1, resp.body?.id)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun edit() {
        val book = Book(name = "new book", author = "Pushkin")
        bookService.add(book)

        book.name = "updated book"

        val resp =
            restTemplate.postForEntity(
                "http://localhost:${port}/rest/book/${book.id}/edit",
                HttpEntity(book, getHeaders()),
                Book::class.java
            )

        assertNotNull(resp.body?.id)
        assertEquals("updated book", resp.body?.name)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun delete() {
        val book = Book(name = "new book", author = "Pushkin")
        bookService.add(book)

        val resp = restTemplate.exchange(
            "http://localhost:${port}/rest/book/${book.id}/delete",
            HttpMethod.DELETE,
            HttpEntity(null, getHeaders()),
            Boolean::class.java
        )
        resp.body?.let { assertTrue(it) }
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

}