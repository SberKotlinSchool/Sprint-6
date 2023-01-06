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

    @GetMapping("/{id}/view")
    fun viewAddress(@PathVariable id: Int): Address? {
        return service.get(id)
    }

    @PostMapping("/{id}/edit")
    fun editAddress(@PathVariable id: Int, @RequestBody address: Address) {
        service.edit(id, address)
    }

    @PostMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: Int) {
        service.delete(id)
    }

    @PostMapping("/add")
    fun addNewAddress(@RequestBody address: Address) {
        service.add(address)
    }
}