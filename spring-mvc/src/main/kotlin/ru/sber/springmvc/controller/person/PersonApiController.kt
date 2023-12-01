package ru.sber.springmvc.controller.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.model.Person
import ru.sber.springmvc.service.PersonService

@RestController
@RequestMapping("/api")
class PersonApiController @Autowired constructor(private val personService: PersonService) {

    @PostMapping("/add")
    fun add(@RequestBody person: Person): ResponseEntity<Person> {
        personService.add(person)
        return ResponseEntity.ok(person)
    }

    @GetMapping("/list")
    fun getAll(@RequestParam(required = false) name: String?): ResponseEntity<List<Person>> {
        return if (name == null) {
            ResponseEntity.ok(personService.getAll())
        } else {
            ResponseEntity.ok(personService.getAllContains(name))
        }
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Int): ResponseEntity<Person?> {
        return ResponseEntity.ok(personService.getById(id))
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @RequestBody person: Person): ResponseEntity<Person?> {
        personService.update(person.copy(id = id))
        return ResponseEntity.ok(person)
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) {
        personService.delete(id)
    }
}