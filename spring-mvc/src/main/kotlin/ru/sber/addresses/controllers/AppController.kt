package ru.sber.addresses.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.addresses.requests.CreateAddressRq
import ru.sber.addresses.services.AddressService
import javax.validation.Valid

@Controller
@RequestMapping("app")
class AppController(private val addressService: AddressService) {
    @GetMapping("list")
    fun getAddresses(model: Model): String {
        model.addAttribute("addresses", addressService.getAddresses())
        return "list"
    }

    @GetMapping("add")
    fun showAdd(model: Model): String {
        model.addAttribute("address", CreateAddressRq())
        return "add"
    }

    @PostMapping("add")
    fun createAddress(
        @Valid @ModelAttribute("address") address: CreateAddressRq,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "add"
        }
        addressService.createAddress(address)
        return "redirect:/app/list"
    }

    @GetMapping("{id}/view")
    fun getAddress(@PathVariable("id") addressId: Long, model: Model): String {
        val addressList = addressService.getAddresses(addressId)
        return if (addressList.isNotEmpty()) {
            model.addAttribute("address", addressList.first())
            "view"
        } else {
            "redirect:/app/list"
        }
    }

    @GetMapping("{id}/edit")
    fun editAddress(@PathVariable("id") addressId: Long, model: Model): String {
        val addressList = addressService.getAddresses(addressId)
        return if (addressList.isNotEmpty()) {
            model.addAttribute("address", addressList.first())
            "edit"
        } else {
            "redirect:/app/list"
        }
    }

    @PostMapping("{id}/edit")
    fun editAddress(
        @PathVariable id: Long,
        @Valid @ModelAttribute("address") address: CreateAddressRq,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "edit"
        }
        addressService.updateAddress(id, address)
        return "redirect:/app/list"
    }

    @GetMapping("{id}/delete")
    fun deleteAddress(@PathVariable("id") addressId: Long, model: Model): String {
        addressService.deleteAddress(addressId)
        return "redirect:/app/list"
    }
}