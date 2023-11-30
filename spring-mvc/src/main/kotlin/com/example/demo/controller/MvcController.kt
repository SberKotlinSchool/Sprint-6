package com.example.demo.controller

import com.example.demo.service.AddressBookModel
import com.example.demo.service.AddressBookService
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/app")
class MvcController(private val service: AddressBookService) {

    @GetMapping("/list")
    fun list(model: Model): String {
        model.addAttribute("addressList", service.getAll())
        return "list"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute addressBookModel: AddressBookModel): String {
        service.add(addressBookModel)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String, model: Model): String {
        val addressBookModel = service.get(id) ?: throw ResponseStatusException(HttpStatusCode.valueOf(404))
        model.addAttribute("address", addressBookModel)
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String, model: Model): String {
        val addressBookModel = service.get(id) ?: throw ResponseStatusException(HttpStatusCode.valueOf(404))
        model.addAttribute("address", addressBookModel)
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@ModelAttribute addressBookModel: AddressBookModel, @PathVariable id: String, model: Model): String {
        service.add(addressBookModel)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: String): String {
        service.delete(id)
        return "redirect:/app/list"
    }
}
