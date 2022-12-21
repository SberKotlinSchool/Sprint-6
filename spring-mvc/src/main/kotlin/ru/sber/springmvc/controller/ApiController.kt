package ru.sber.springmvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import ru.sber.springmvc.model.AddressBookRow
import ru.sber.springmvc.service.AddressBookService

@RestController
@RequestMapping("/api")
class ApiController(private val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun add(@RequestBody row: AddressBookRow, uri: UriComponentsBuilder): ResponseEntity<Nothing> {
        addressBookService.add(row)
        return ResponseEntity
            .created(uri.path("/api/{id}/view").build(row.name))
            .build()
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) = addressBookService.get(id)

    @GetMapping("/list")
    fun list(): List<AddressBookRow> = addressBookService.getAll()

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: String, @RequestBody address: String): ResponseEntity<Nothing> {
        addressBookService.edit(id, address)
        return ResponseEntity.accepted().build()
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: String): ResponseEntity<Nothing> {
        addressBookService.delete(id)
        return ResponseEntity.accepted().build()
    }
}
