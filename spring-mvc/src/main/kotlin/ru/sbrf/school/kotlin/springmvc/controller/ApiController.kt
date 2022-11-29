package ru.sbrf.school.kotlin.springmvc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sbrf.school.kotlin.springmvc.entity.Person
import ru.sbrf.school.kotlin.springmvc.repository.AddressBookRepository

@RestController
@RequestMapping("/api")
class ApiController(@Autowired val repository: AddressBookRepository) {

    @GetMapping("/list")
    fun list() : ResponseEntity<List<Person>> {
        return ResponseEntity.ok(repository.getAll())
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) : ResponseEntity<Person> {
        return ResponseEntity.ok(repository.getById(id.toLong()))
    }

    @PostMapping("/add")
    fun add(@RequestBody person: Person) : ResponseEntity<Person> {
        return ResponseEntity.ok(repository.save(person))
    }

    @PutMapping("/{id}/edit")
    fun add(@PathVariable id: String, @RequestBody person: Person) : ResponseEntity<Person> {
        return ResponseEntity.ok(repository.save(person))
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: String, person: Person)  {
        repository.delete(id.toLong())
    }
}