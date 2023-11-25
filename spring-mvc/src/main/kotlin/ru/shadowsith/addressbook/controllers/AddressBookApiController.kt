package ru.shadowsith.addressbook.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.shadowsith.addressbook.dto.Record
import ru.shadowsith.addressbook.services.AddressBookService

@RestController
@RequestMapping("/api/address-book")
class AddressBookApiController(
    private val addressBookService: AddressBookService
) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addRecord(
        @RequestBody record: Record
    ): ResponseEntity<Record?> {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressBookService.add(record))
    }

    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun getRecords(
        @RequestParam name: String?
    ): List<Record> {
        return name?.let { addressBookService.findByName(name) } ?: addressBookService.findAll()
    }

    @GetMapping(
        path = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getRecordById(
        @PathVariable id: String
    ): ResponseEntity<Record?> {
        return addressBookService.findByGuid(id)?.let { ResponseEntity.status(HttpStatus.OK).body(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

    @PutMapping(
        path = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun changeRecord(
        @PathVariable id: String,
        @RequestBody record: Record
    ): ResponseEntity<Record?> {
        val result = addressBookService.change(id, record)
        return result?.let { ResponseEntity.status(HttpStatus.OK).body(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

    @DeleteMapping(
        path = ["/{id}"]
    )
    fun deleteRecord(
        @PathVariable id: String,
    ): ResponseEntity<Record?> {
        val result = addressBookService.delete(id)
        return result?.let { ResponseEntity.status(HttpStatus.OK).body(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

}