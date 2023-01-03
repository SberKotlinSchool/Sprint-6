package ru.sber.mvc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.mvc.data.Contact
import ru.sber.mvc.service.AddressBookService

@Controller
@RequestMapping("/app")
class AddressBookController(@Autowired val service : AddressBookService) {

    @GetMapping("/list")
    fun getAddressBook(model: Model): String {
        model.addAttribute("contacts", service.getAll())
        return "addressbook"
    }

    @GetMapping("/add")
    fun create(model: Model): String {
        model.addAttribute("contact", Contact())
        model.addAttribute("command", "/app/add")
        return "contact"
    }

    @PostMapping("/add")
    fun create(@ModelAttribute contact: Contact): String {
        service.add(contact)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/view")
    fun read(@PathVariable id: Long, model: Model): String {
        model.addAttribute("contact", service.getById(id))
        model.addAttribute("command", "/app/list")
        return "contact"
    }

    @GetMapping("/{id}/edit")
    fun update(@PathVariable id: Long, model: Model): String {
        model.addAttribute("contact", service.getById(id))
        model.addAttribute("command", "/app/edit")
        return "contact"
    }

    @PostMapping("/{id}/edit")
    fun update(@PathVariable id: Long, @ModelAttribute contact: Contact): String {
        service.update(id, contact)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/app/list"
    }
}