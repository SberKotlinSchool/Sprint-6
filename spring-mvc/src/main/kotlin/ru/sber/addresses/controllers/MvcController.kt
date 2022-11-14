package ru.sber.addresses.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.addresses.requests.CreateAddressRq
import ru.sber.addresses.services.AddressService

@Controller
@RequestMapping("app")
class MvcController(private val addressService: AddressService) {
    @GetMapping("add")
    fun addAddress(model: Model): String {
        return "add"
    }

    @PostMapping("create")
    fun createAddress(@ModelAttribute address: CreateAddressRq, model: Model): String {
        val result = addressService.createAddress(address)
        model.addAttribute("id", result.id)
        return "processCreate"
    }

    @GetMapping("{id}/view")
    fun getAddress(@PathVariable("id") addressId: Long, model: Model): String {
        val addressList = addressService.getAddresses(addressId)
        if (addressList.isNotEmpty()) {
            val address = addressList.first()
            model.addAttribute("fullName", address!!.fullName)
            model.addAttribute("postAddress", address.postAddress)
            model.addAttribute("phoneNumber", address.phoneNumber)
        }
        return "processView"
    }
}