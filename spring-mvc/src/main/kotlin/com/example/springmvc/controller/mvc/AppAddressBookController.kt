package com.example.springmvc.controller.mvc

import com.example.springmvc.model.Contact
import com.example.springmvc.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Controller
@RequestMapping("/app")
class AppAddressBookController {

    @Autowired
    private lateinit var contactRepository: ContactRepository

    @GetMapping("/list")
    fun listAllContacts(model: Model): String {
        model.addAttribute("contacts", contactRepository.list())
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewContact(@PathVariable id: Int, model: Model): String {
        model.addAttribute("contact", contactRepository.view(id)
            ?: return "contactError")
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun editContact(@PathVariable id: Int, model: Model): String {
        model.addAttribute("contact", contactRepository.view(id)
            ?: return "contactError")
        return "edit"
    }

    @PatchMapping("/{id}/edit")
    fun edit(@Valid @ModelAttribute editedContact: Contact,
             bindingResult: BindingResult): String {

        if (bindingResult.hasErrors()) {
            return "edit"
        }

        contactRepository.edit(editedContact)
        return "view"
    }

    @DeleteMapping("/{id}/delete")
    fun deleteContact(@PathVariable id: Int): String {
        return if (contactRepository.delete(id)) {
            "redirect:/app/list"
        } else {
            "contactError"
        }
    }

    @GetMapping("/add")
    fun addContact(@ModelAttribute contact: Contact): String {
        return "add"
    }

    @PostMapping("/add")
    fun add(@Valid @ModelAttribute contact: Contact,
            bindingResult: BindingResult): String {

        if (bindingResult.hasErrors()) {
            return "add"
        }

        contactRepository.add(contact)
        return "redirect:/app/list"
    }
}