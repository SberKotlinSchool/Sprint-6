package ru.sber.spring.mvc.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.MultiValueMap
import ru.sber.spring.mvc.model.Book
import ru.sber.spring.mvc.service.BookService
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest
internal class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookService: BookService

    private val mapper = jacksonObjectMapper()

    private fun getHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        val expDate = LocalDateTime.now().plusMinutes(5).toString()
        httpHeaders.add("Cookie", "auth=${expDate}")
        return httpHeaders
    }

    @Test
    fun getMainPage() {
        mockMvc.perform(get("/book").headers(getHeaders()))
            .andDo(print())
            .andExpect(view().name("main"))
            .andExpect(status().isFound)
    }

    @Test
    fun getAddBookPage() {
        mockMvc.perform(get("/book/add").headers(getHeaders()))
            .andDo(print())
            .andExpect(view().name("add"))
            .andExpect(status().isFound)
    }

    @Test
    fun addBook() {
        val book = Book(name = "new book", author = "Pushkin")

        mockMvc.perform(
            post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getHeaders())
                .content(mapper.writeValueAsBytes(book)))
            .andDo(print())
            .andExpect(view().name("main"))
            .andExpect(status().isFound)
    }

    @Test
    fun getBooksList() {
        bookService.add(Book(name = "new book 1", author = "Pushkin"))
        bookService.add(Book(name = "new book 2", author = "Pushkin"))

        mockMvc.perform(get("/book/list").headers(getHeaders()))
            .andDo(print())
            .andExpect(view().name("list"))
            .andExpect(status().isFound)
    }

    @Test
    fun viewById() {
        val book = Book(name = "new book 1", author = "Pushkin")
        bookService.add(book)

        mockMvc.perform(get("/book/${book.id}/view").headers(getHeaders()))
            .andDo(print())
            .andExpect(view().name("view"))
            .andExpect(status().isFound)
    }

    @Test
    fun edit() {
        val book = Book(name = "new book", author = "Pushkin")
        bookService.add(book)

        book.name = "updated book"

        mockMvc.perform(
            post("/book/${book.id}/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getHeaders())
                .content(mapper.writeValueAsBytes(book)))
            .andDo(print())
            .andExpect(view().name("main"))
            .andExpect(status().isFound)

        val newBook = bookService.getById(book.id!!)!!
        assertEquals("updated book", newBook.name)
    }

    @Test
    fun delete() {
        val book = Book(name = "new book", author = "Pushkin")
        bookService.add(book)

        mockMvc.perform(delete("/book/${book.id}/delete").headers(getHeaders()))
            .andDo(print())
            .andExpect(view().name("list"))
            .andExpect(status().isFound)

        assertEquals(0, bookService.getBooks().size)
    }
}