package ru.sber.springmvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import ru.sber.springmvc.service.BookRow
import ru.sber.springmvc.service.BookService


@RestController
@RequestMapping("/api")
class ApiController(private val bookService: BookService) {

    @PostMapping("/add")
    fun add(@RequestBody row: BookRow, uri: UriComponentsBuilder): ResponseEntity<Nothing> {
        bookService.add(row)
        return ResponseEntity.created(uri.path("/api/{id}/view").build(row.name)).build()
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) = bookService.get(id)

    @GetMapping("/list")
    fun list(): List<BookRow> = bookService.getAll()

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: String, @RequestBody address: String): ResponseEntity<Nothing> {
        bookService.edit(id, address)
        return ResponseEntity.accepted().build()
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: String): ResponseEntity<Nothing> {
        bookService.delete(id)
        return ResponseEntity.accepted().build()
    }
}