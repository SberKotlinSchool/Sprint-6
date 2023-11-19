package com.example.mvcexampleproject.controllers

import com.example.mvcexampleproject.domain.Address
import com.example.mvcexampleproject.services.AddressService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest")
class AddressRestController(private val service: AddressService) {

    @GetMapping("/list")
    fun getAddressList(@RequestParam(required = false) query: String?): List<Address> {
        return service.getList(query)
    }

    @PostMapping
    fun addNewAddress(@RequestBody address: Address) {
        service.add(address)
    }

    @GetMapping("/{id}")
    fun viewAddress(@PathVariable id: Int): Address? {
        return service.get(id)
    }

    @PutMapping("/{id}")
    fun editAddress(@PathVariable id: Int, @RequestBody address: Address) {
        service.edit(id, address)
    }

    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: Int) {
        service.delete(id)
    }

}