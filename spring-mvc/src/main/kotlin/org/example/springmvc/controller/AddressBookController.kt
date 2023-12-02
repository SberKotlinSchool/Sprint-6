package org.example.springmvc.controller

import org.example.springmvc.entity.Contact
import org.example.springmvc.service.AddressBookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class AddressBookController(private val service: AddressBookService) {

    @GetMapping("/list")
    fun getContacts(@RequestParam(required = false) city: String?, model: Model): String {
        model.addAttribute("contactList", service.findContactsByCity(city))
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewContact(@PathVariable id: Int, model: Model): String {
        model.addAttribute("contact", service.get(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun getFormEditContact(@PathVariable id: Int, model: Model): String {
        model.addAttribute("contact", service.get(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editContact(@PathVariable id: Int, @ModelAttribute contact: Contact, model: Model): String {
        service.edit(id, contact)
        return "redirect:/app/list"
    }

    @PostMapping("/{id}/delete")
    fun deleteContact(@PathVariable id: Int): String {
        service.delete(id)
        return "redirect:/app/list"
    }

    @GetMapping("/add")
    fun getFormNewContact(@ModelAttribute contact: Contact, model: Model): String {
        model.addAttribute("contact", contact)
        return "add"
    }

    @PostMapping("/add")
    fun addContact(@ModelAttribute contact: Contact): String {
        service.add(contact)
        return "redirect:/app/list"
    }
}