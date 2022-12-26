package ru.sber.addressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sber.addressbook.model.Person
import ru.sber.addressbook.service.PersonService

@RestController
@RequestMapping("/api")
class ApiController @Autowired constructor(val personService: PersonService) {

    @GetMapping("/list")
    fun list(): ResponseEntity<List<Person>> {
        return ResponseEntity.ok(personService.getAll())
    }

    @PostMapping("/add")
    fun add(person: Person): ResponseEntity<Long> {
        return ResponseEntity.ok(personService.add(person))
    }

    @GetMapping("/{id}/view")
    fun getById(@PathVariable id: Long): ResponseEntity<Person> {
        return ResponseEntity.ok(personService.getById(id))
    }

    @PostMapping("/edit")
    fun update(person: Person): Person {
        return personService.update(person)
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        personService.delete(id)
        return ResponseEntity.ok().body("Ok")
    }
}