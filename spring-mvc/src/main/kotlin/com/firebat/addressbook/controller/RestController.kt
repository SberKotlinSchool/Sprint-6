package com.firebat.addressbook.controller

import com.firebat.addressbook.model.Entry
import com.firebat.addressbook.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addEntry(@RequestBody entry: Entry): ResponseEntity<Long> {
        return ResponseEntity.ok(addressBookService.addEntry(entry))
    }

    @GetMapping("/list")
    fun getEntries(@RequestParam(required = false) query: String?): ResponseEntity<Set<Map.Entry<Long, Entry>>> {
        return ResponseEntity.ok(addressBookService.getEntries(query))
    }

    @GetMapping("{id}/view")
    fun viewEntry(@PathVariable id: Long): ResponseEntity<Entry> {
        return ResponseEntity.ok().body(
            addressBookService.findEntryById(id)
                ?: return ResponseEntity.notFound().build()
        )
    }

    @PutMapping("{id}/edit")
    fun editEntry(@PathVariable id: Long, @RequestBody entry: Entry): ResponseEntity<Entry> {
        return ResponseEntity.ok().body(
            addressBookService.editEntry(id, entry)
                ?: return ResponseEntity.notFound().build()
        )
    }

    @DeleteMapping("/{id}/delete")
    fun deleteEntry(@PathVariable id: Long): ResponseEntity<Entry> {
        return ResponseEntity.ok().body(
            addressBookService.deleteEntry(id)
                ?: return ResponseEntity.notFound().build()
        )
    }
}