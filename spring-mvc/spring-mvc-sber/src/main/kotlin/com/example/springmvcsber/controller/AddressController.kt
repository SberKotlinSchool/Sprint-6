package com.example.springmvcsber.controller

import com.example.springmvcsber.entity.Address
import com.example.springmvcsber.service.AddressService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class AddressController(private val service: AddressService) {

    @GetMapping("/list")
    fun getAddresses(@RequestParam(required = false) city: String?, model: Model): String {
        model.addAttribute("addressList", service.findAddressesByCity(city))
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewAddress(@PathVariable id: Int, model: Model): String {
        model.addAttribute("address", service.get(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun getFormEditAddress(@PathVariable id: Int, model: Model): String {
        model.addAttribute("address", service.get(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editAddress(@PathVariable id: Int, @ModelAttribute address: Address, model: Model): String {
        service.edit(id, address)
        return "redirect:/app/list"
    }

    @PostMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: Int): String {
        service.delete(id)
        return "redirect:/app/list"
    }

    @GetMapping("/add")
    fun getFormNewAddress(@ModelAttribute address: Address, model: Model): String {
        model.addAttribute("address", address)
        return "add"
    }

    @PostMapping("/add")
    fun addNewAddress(@ModelAttribute address: Address): String {
        service.add(address)
        return "redirect:/app/list"
    }
}