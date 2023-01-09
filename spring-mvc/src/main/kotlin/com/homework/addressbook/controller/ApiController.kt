package com.homework.addressbook.controller;

import com.homework.addressbook.dto.Record
import com.homework.addressbook.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api")
class ApiController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addRecord(@RequestBody record: Record): ResponseEntity<Any> {
        addressBookService.addRecord(record);
        return ResponseEntity.ok().build()
    }

    @GetMapping("/list")
    fun getRecords(): ResponseEntity<Any> {
        return ResponseEntity.ok(addressBookService.getRecords());
    }

    @GetMapping("/{id}/view")
    fun getCurrentRecord(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity.ok(addressBookService.getCurrentRecord(id));
    }

    @PostMapping("/{id}/edit")
    fun editRecord(@PathVariable id: Int, @RequestBody record: Record): ResponseEntity<Any> {
        addressBookService.editRecord(id, record)
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}/delete")
    fun deleteRecord(@PathVariable id: Int): ResponseEntity<Any> {
        addressBookService.deleteRecord(id);
        return ResponseEntity.ok().build();
    }
}
