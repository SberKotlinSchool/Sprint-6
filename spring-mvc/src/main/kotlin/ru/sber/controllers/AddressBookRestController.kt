package ru.sber.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sber.model.Person
import ru.sber.service.AddressBookService

@RestController
@RequestMapping("/api")
class AddressBookRestController @Autowired constructor(val service: AddressBookService) {

    @PostMapping("/add")
    fun addPerson(@RequestBody person: Person): ResponseEntity<Person> {
        service.add(person)
        return ResponseEntity.ok(person)
    }

    @GetMapping("/list")
    fun getPersons(
        @RequestParam(required = false) fio: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?,
        model: Model
    ): ResponseEntity<List<Person>> {
        return ResponseEntity.ok(service.getPersons(fio, address, phone, email))
    }

    @GetMapping("/{id}/view")
    fun viewPerson(@PathVariable id: Int): ResponseEntity<Person?> {
        return ResponseEntity.ok(service.getPersonById(id))
    }

    @PutMapping("/{id}/edit")
    fun editPerson(@RequestBody person: Person, result: BindingResult): ResponseEntity<Person?> {
        service.updatePerson(person)
        return ResponseEntity.ok(person)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePerson(@PathVariable id: Int) {
        service.deletePerson(id)
    }
}