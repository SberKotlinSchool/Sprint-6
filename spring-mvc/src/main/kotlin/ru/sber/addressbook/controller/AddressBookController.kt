package ru.sber.addressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.addressbook.data.Contact
import ru.sber.addressbook.service.AddressBookService
import javax.servlet.http.HttpServletRequest

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
    fun create(@ModelAttribute("contact") contact: Contact): String {
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
    fun update(@PathVariable id: Long, model: Model, request: HttpServletRequest): String {
        model.addAttribute("contact", service.getById(id))
        model.addAttribute("command", request.requestURI)
        return "contact"
    }

    @PostMapping("/{id}/edit")
    fun update(@PathVariable id: Long, @ModelAttribute("contact") contact: Contact): String {
        service.update(id, contact)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/app/list"
    }
}