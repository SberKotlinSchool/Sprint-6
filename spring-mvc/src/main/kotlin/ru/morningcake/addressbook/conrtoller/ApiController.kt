package ru.morningcake.addressbook.conrtoller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import ru.morningcake.addressbook.service.NoteService
import ru.morningcake.addressbook.validation.noteDtoValidation
import java.util.*

@RestController
@RequestMapping("/api/note")
class ApiController @Autowired constructor(private val service: NoteService) {

    @GetMapping("/list")
    @ResponseBody
    fun getBook(@RequestParam filter: String?): ResponseEntity<List<Note>> {
        return if (filter == null || filter.isBlank()) {
            ResponseEntity.ok(service.getAll())
        } else {
            ResponseEntity.ok(service.search(filter))
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getNote(@PathVariable id : UUID): ResponseEntity<Note> {
        return ResponseEntity.ok(service.getById(id))
    }

    @PostMapping
    @ResponseBody
    fun createNote(@RequestBody dto : NoteDto): ResponseEntity<Note> {
        noteDtoValidation(dto)
        return ResponseEntity.ok(service.create(dto))
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun updateNote(@RequestBody dto : NoteDto, @PathVariable id : UUID): ResponseEntity<Note> {
        noteDtoValidation(dto)
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id : UUID) : ResponseEntity<String> {
        service.delete(id)
        return ResponseEntity.ok("Запись удалена!")
    }
}