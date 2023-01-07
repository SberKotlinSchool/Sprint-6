package ru.sber.springmvc.controller

import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.model.Address
import ru.sber.springmvc.service.AddressService

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
    fun deleteAddress(@PathVariable id: Int) {
        service.delete(id)
    }
}