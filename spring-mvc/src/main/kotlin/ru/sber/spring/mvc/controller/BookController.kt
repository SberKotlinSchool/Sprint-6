package ru.sber.spring.mvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.spring.mvc.model.Book
import ru.sber.spring.mvc.service.BookService

@Controller
@RequestMapping(value = ["/book"])
class BookController(private val bookService: BookService) {

    @GetMapping()
    fun getMainPage(model: Model): String {

        return "main"
    }

    @GetMapping("/add")
    fun getAddBookPage(model: Model): String {
        model.addAttribute("book", Book())
        return "add"
    }

    @PostMapping("/add")
    fun addBook(@ModelAttribute("book") book: Book): String {
        bookService.add(book)
        return "main"
    }

    @GetMapping("/list")
    fun getBooksList(model: Model): String {
        val books: List<Book> = bookService.getBooks()
        model.addAttribute("books", books)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewById(model: Model, @PathVariable id: Int): String {
        val book: Book = bookService.getById(id) ?: throw Error("Book is not found")
        model.addAttribute("book",book)
        return "view"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @RequestBody book: Book): String {
        val entity: Book = bookService.getById(id) ?: throw Error("Book is not found")
        with(entity) {
            name = book.name
            author= book.author
        }
        bookService.update(entity)
        return "main"
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Int):String {
        bookService.delete(id)
        return "list"
    }

}