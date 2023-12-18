package ru.sber.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.model.Person
import ru.sber.service.AddressBookService
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/api")
class AddressBookRestController(private val addressBookService: AddressBookService) {
    @PostMapping("/add")
    fun addNewPerson(@RequestBody person: Person): ResponseEntity<Any> {
        return ResponseEntity.ok(addressBookService.addNewPerson(person))
    }

    @GetMapping("/list")
    fun showAllPersons(): ResponseEntity<List<Person>> {
        return ResponseEntity.ok(addressBookService.getAllPersons())
    }

    @GetMapping("/{id}/view")
    fun showPersonById(@PathVariable id: Long): ResponseEntity<Person> {
        return when (val person = addressBookService.getPersonById(id)) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(person)
        }
    }

    @PostMapping("/{id}/edit")
    fun updatePersonById(@PathVariable id: Long, @RequestBody person: Person): ResponseEntity<Any> {
        return ResponseEntity.ok(addressBookService.updatePersonInfo(id, person))
    }

    @GetMapping("/{id}/delete")
    fun deletePersonById(@PathVariable id: Long) {
        return addressBookService.deletePerson(id)
    }

}