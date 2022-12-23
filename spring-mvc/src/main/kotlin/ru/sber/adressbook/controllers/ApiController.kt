package ru.sber.adressbook.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import ru.sber.adressbook.services.BookService
import ru.sber.adressbook.vo.DataBook
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class ApiController @Autowired constructor(val bookService: BookService) {

    @PostMapping("/add")
    fun addAddress(@RequestBody book: DataBook): ResponseEntity<*> {
        return ResponseEntity(bookService.addBook(book), HttpStatus.CREATED)
    }

    @GetMapping("/list")
    fun listOfBooks(@RequestParam(required = false) allParams: Map<String, String>): ResponseEntity<ConcurrentHashMap<Int, DataBook>> {
        val ads: ConcurrentHashMap<Int, DataBook> = bookService.getListBooks()
        return ResponseEntity(ads, HttpStatus.OK)
    }

    @GetMapping("/{id}/view")
    fun viewBook(@PathVariable("id") id: Int): ResponseEntity<DataBook> {
        val book = bookService.getBook(id)
        return ResponseEntity(book, HttpStatus.FOUND)
    }

    @PutMapping("/{id}/edit")
    fun editBook(@PathVariable("id") id: Int, @RequestBody book: DataBook): ResponseEntity<*> {
        if (bookService.editBook(index = id, dataBook = book) != null)
            return ResponseEntity(book, HttpStatus.OK)

        return ResponseEntity(book, HttpStatus.CREATED)
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/delete")
    fun deleteBook(@PathVariable("id") id: Int): ResponseEntity<*> {
        return ResponseEntity(bookService.removeBook(id), HttpStatus.FOUND)
    }
}