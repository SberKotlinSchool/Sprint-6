package sber.mvc.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sber.mvc.application.model.AddressBookEntry
import sber.mvc.application.service.AddressBookService

@RestController
@RequestMapping("/api")
class RestController(@Autowired val service: AddressBookService) {

    @PostMapping(value = ["/add"])
    fun addEntry(@RequestBody entry: AddressBookEntry): ResponseEntity<AddressBookEntry> {
        service.addEntry(entry)
        return ResponseEntity.ok(entry)
    }

    @GetMapping(value = ["/list"])
    fun listEntries(
        @RequestParam(required = false) firstname: String?,
        @RequestParam(required = false) lastname: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<List<AddressBookEntry>> {
        return ResponseEntity.ok(service.getEntries(firstname, lastname, address, phone, email))
    }

    @GetMapping(value = ["/{id}/view"])
    fun viewEntry(@PathVariable id: Long): ResponseEntity<AddressBookEntry?> {
        return ResponseEntity.ok(service.getEntryById(id))
    }

    @PutMapping(value = ["/{id}/edit"])
    fun editEntry(
        @PathVariable id: Int,
        @RequestBody entry: AddressBookEntry
    ): ResponseEntity<AddressBookEntry?> {
        service.updateEntry(entry)
        return ResponseEntity.ok(entry)
    }

    @DeleteMapping(value = ["/{id}/delete"])
    fun deleteEntry(@PathVariable id: Long) {
        service.deleteEntryById(id)
    }
}