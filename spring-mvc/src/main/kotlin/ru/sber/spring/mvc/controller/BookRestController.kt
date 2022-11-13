package ru.sber.spring.mvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.spring.mvc.model.Book
import ru.sber.spring.mvc.service.BookService


@RestController
@RequestMapping("/rest/book")
class BookRestController(private val bookService: BookService) {

    @PostMapping("/add")
    fun add(@RequestBody book: Book): ResponseEntity<Book> {
        bookService.add(book)
        return ResponseEntity.ok(book)
    }

    @GetMapping("/list")
    fun list(): ResponseEntity<List<Book>> {
        val books: List<Book> = bookService.getBooks()
        return ResponseEntity.ok(books)
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Int): ResponseEntity<Book> {
        val book: Book = bookService.getById(id) ?: throw Error("Book is not found")
        return ResponseEntity.ok(book)
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @RequestBody book: Book): ResponseEntity<Book> {
        val entity: Book = bookService.getById(id) ?: throw Error("Book is not found")
        with(entity) {
            name = book.name
            author= book.author
        }
        bookService.update(entity)
        return ResponseEntity.ok(book)
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Int): ResponseEntity<Boolean> {
        val res:Boolean =  bookService.delete(id)
        return ResponseEntity.ok(res)
    }

}