package ru.sber.springmvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sber.springmvc.domain.Person
import ru.sber.springmvc.service.PersonService

@RestController
@RequestMapping("/api")
class ApiController(private val personService: PersonService) {
    @GetMapping("/list")
    fun getAll(@RequestParam(required = false) name: String?): ResponseEntity<List<Person>> {
        return if (name == null) {
            ResponseEntity.ok(personService.getAll())
        } else {
            ResponseEntity.ok(personService.getByName(name))
        }
    }

    @GetMapping("/person/{id}")
    fun getPersonById(@PathVariable id: Int): ResponseEntity<Person?> {
        return ResponseEntity.ok(personService.getById(id))
    }

    @PostMapping("/person/add")
    fun addPerson(@RequestBody person: Person): ResponseEntity<Person> {
        personService.addPerson(person)
        return ResponseEntity.ok(person)
    }

    @PutMapping("/person/{id}/edit")
    fun editPerson(@PathVariable id: Int, @RequestBody person: Person): ResponseEntity<Person> {
        personService.updatePerson(person.copy(id = id))
        return ResponseEntity.ok(person)
    }

    @DeleteMapping("/person/{id}/delete")
    fun deletePerson(@PathVariable id: Int) {
        personService.deletePerson(id)
    }
}