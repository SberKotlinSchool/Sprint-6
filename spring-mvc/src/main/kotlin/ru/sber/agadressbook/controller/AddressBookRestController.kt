package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService

@RestController
@RequestMapping("/addressbook/api")
class AddressBookRestController @Autowired constructor(
    val addressBookWebService: AddressBookWebService) {



    @PostMapping("/add")
    fun create(@RequestBody person: Person): ResponseEntity<Person> {
        addressBookWebService.addRecord(person)
        return  ResponseEntity.ok(person)
    }

    @PatchMapping("{id}/edit")
    fun updateRecord(@PathVariable("id") id: Int, @RequestBody person: Person): ResponseEntity<Person> {
        addressBookWebService.updateRecord(id, person)
        return ResponseEntity.ok(person)
    }

    @GetMapping("list")
    fun getAllRecords(): ResponseEntity<List<Person>> {
            return ResponseEntity.ok(addressBookWebService.getAllRecords().toList().map { it.second })
    }

    @GetMapping("{id}/view")
    fun viewRecord(@PathVariable("id") id: Int): ResponseEntity<Person> {
        return ResponseEntity.ok(addressBookWebService.getRecordById(id))
    }

    @DeleteMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Person> {
        addressBookWebService.deleteRecord(id)
        return  ResponseEntity.ok(addressBookWebService.deleteRecord(id))
    }
}