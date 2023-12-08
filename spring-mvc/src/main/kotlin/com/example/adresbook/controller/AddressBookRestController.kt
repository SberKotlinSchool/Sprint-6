package com.example.adresbook.controller

import com.example.adresbook.model.BookRecord
import com.example.adresbook.service.AddressBookService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AddressBookRestController(private val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun add(bookRecord: BookRecord) = addressBookService.addBookRecord(bookRecord)

    @GetMapping("/list")
    fun list(
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        model: Model
    ) = addressBookService.getList(address, phone)

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Long) = addressBookService.getBookRecord(id)

    @PostMapping("/{id}/edit")
    fun edit(
        @PathVariable id: Int,
        @RequestBody bookRecord: BookRecord,
    ) = addressBookService.editBookRecord(bookRecord, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = addressBookService.deleteBookRecord(id)
}