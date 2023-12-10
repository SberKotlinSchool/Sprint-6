package com.example.springmvcsber.controller

import com.example.springmvcsber.entity.Address
import com.example.springmvcsber.service.AddressService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest")
class AddressRestController(private val service: AddressService)  {

    @GetMapping("/list")
    fun getAddresses(@RequestParam(required = false) city: String?): List<Address> {
        return service.findAddressesByCity(city)
    }

    @PostMapping
    fun addAddress(@RequestBody address: Address) {
        service.add(address)
    }

    @GetMapping("/{id}")
    fun viewAddress(@PathVariable id: Long): Address? {
        return service.get(id)
    }

    @PutMapping("/{id}")
    fun editAddress(@PathVariable id: Long, @RequestBody address: Address) {
        service.edit(id, address)
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteAddress(@PathVariable id: Long) {
        service.delete(id)
    }
}