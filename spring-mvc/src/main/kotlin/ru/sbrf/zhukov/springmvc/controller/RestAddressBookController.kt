package ru.sbrf.zhukov.springmvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sbrf.zhukov.springmvc.data.AddressEntry
import ru.sbrf.zhukov.springmvc.service.AddressBookService

@RestController
@RequestMapping("/api")
class RestAddressBookController(
    private val addressBookService: AddressBookService
) {

    @GetMapping("/list")
    fun listEntries(): List<AddressEntry> {
        return addressBookService.getAllEntries()
    }

    @GetMapping("/{id}")
    fun getEntry(@PathVariable id: Long): ResponseEntity<AddressEntry> {
        val entry = addressBookService.getEntry(id)
        return if (entry != null) {
            ResponseEntity.ok(entry)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/add")
    fun addEntry(@RequestBody entry: AddressEntry): ResponseEntity<AddressEntry> {
        val newEntry = addressBookService.addEntry(entry)
        return ResponseEntity.ok(newEntry)
    }

    @PutMapping("/{id}")
    fun editEntry(@PathVariable id: Long, @RequestBody entry: AddressEntry): ResponseEntity<AddressEntry> {
        val editedEntry = addressBookService.editEntry(id, entry)
        return if (editedEntry != null) {
            ResponseEntity.ok(editedEntry)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEntry(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = addressBookService.deleteEntry(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
