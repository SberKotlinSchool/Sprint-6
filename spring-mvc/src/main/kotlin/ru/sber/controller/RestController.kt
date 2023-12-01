package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.BodyBuilder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import ru.sber.dto.Address
import ru.sber.repository.AddressBook

@RestController
@RequestMapping("/api")
class RestController(@Autowired private val addressBook: AddressBook) {
    @GetMapping("/list")
    fun view(@RequestParam(required = false) name: String?) = ResponseEntity.ok(
        if (name.isNullOrEmpty())
            addressBook.getAll()
        else
            addressBook.getAll().filter { it.second.name == name } )
    @PostMapping("/add")
    fun add(@RequestBody address: Address) = ResponseEntity.ok(addressBook.add(address))

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) = ResponseEntity.ok(addressBook.delete(id))

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @RequestBody address: Address) = ResponseEntity.ok(
        addressBook.update(id, address)
    )
    @GetMapping("/{id}/view")
    fun detail(@PathVariable id: Int) = ResponseEntity.ok(
        addressBook.getById(id)
    )
}