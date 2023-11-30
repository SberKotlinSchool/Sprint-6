package com.example.demo.controller

import com.example.demo.service.AddressBookModel
import com.example.demo.service.AddressBookService
import org.springframework.http.HttpStatusCode
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class RestApiController(private val service: AddressBookService) {

    @PostMapping("/add")
    fun add(@RequestBody addressBookModel: AddressBookModel) {
        service.add(addressBookModel)
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String, model: Model): AddressBookModel {
        return service.get(id) ?: throw ResponseStatusException(HttpStatusCode.valueOf(404))
    }

    @GetMapping("/list")
    fun list(model: Model): List<AddressBookModel> {
        return service.getAll()
    }

    @PostMapping("/{id}/edit")
    fun edit(@RequestBody addressBookModel: AddressBookModel, @PathVariable id: String, model: Model): AddressBookModel {
        return service.edit(id, addressBookModel) ?: throw ResponseStatusException(HttpStatusCode.valueOf(404))
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: String) {
        service.delete(id)
    }
}