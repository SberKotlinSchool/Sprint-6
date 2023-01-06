package ru.sber.springmvc.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.model.Address
import ru.sber.springmvc.service.AddressService

@Controller
@RequestMapping("/app")
class AddressMvcController(private val service: AddressService) {

    @GetMapping("/list")
    fun getAddressList(@RequestParam(required = false) query: String?, model: Model): String {
        model.addAttribute("addressList", service.getList(query))
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