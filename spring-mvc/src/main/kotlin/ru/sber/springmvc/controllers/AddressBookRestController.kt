package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record

@RestController
@RequestMapping("/api")
class AddressBookRestController @Autowired constructor(val addressBook: AddressBookRepository) {
    @PostMapping("/add")
    fun addRecord(@RequestBody record: Record): ResponseEntity<Record> {
        return ResponseEntity.ok(addressBook.addRecord(record))
    }

    @GetMapping("/list")
    fun showAll(): ResponseEntity<List<Record>> {
        return ResponseEntity.ok(addressBook.getAll())
    }

    @GetMapping("/{id}/view")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<Record?> {
        return ResponseEntity.ok(addressBook.getById(id))
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable(name = "id") id: Long) {
        addressBook.deleteById(id)
    }
}