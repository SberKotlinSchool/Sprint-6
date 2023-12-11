package com.example.adressbook.controller

import com.example.adressbook.dto.AddressModel
import com.example.adressbook.service.AddressService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/app")
class AddressController(
    private val addressService: AddressService
) {

    @PostMapping("/add")
    fun addAdress(address: AddressModel): String {
        addressService.saveAddress(address)
        println("Адрес $address успешно сохранен")
        return "redirect:/app/list"
    }

    @GetMapping("/add")
    fun addAdress(model: Model): String {
        model.addAttribute("address", AddressModel())
        return "create-address"
    }


    @GetMapping("/list")
    fun viewListOfAddresses(model: Model): String {
        val addresses = addressService.getAddressList()
        println(addresses)
        model.addAttribute("addresses", addresses)
        return "addresses"
    }

    @GetMapping("{id}")
    fun viewAddress(@PathVariable id: Long, model: Model): String {
        model.addAttribute("address", addressService.getAddressById(id))
        return "viewAddress"
    }

    @GetMapping("{id}/edit")
    fun editAddress(@PathVariable id: Long, model: Model): String {
        val address = addressService.getAddressById(id)
        println("Адрес $address успешно получен")
        model.addAttribute("address", address)
        model.addAttribute("id", id)
        return "update-address"
    }

    @PutMapping("{id}/edit")
    fun editAddress(@PathVariable id: Long, @ModelAttribute address: AddressModel): String {
        addressService.updateAddress(id, address)
        println("Адрес $address с id=$id успешно обновлен")
        return "redirect:/app/list"
    }

    @DeleteMapping("{id}/delete")
    fun deleteAddress(@PathVariable id: Long): String {
        addressService.deleteAddressById(id)
        println("Адрес с id=$id успешно удален")
        return "redirect:/app/list"
    }
}