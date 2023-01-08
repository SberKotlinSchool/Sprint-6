package sber.mvc.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import sber.mvc.application.model.AddressBookEntry
import sber.mvc.application.service.AddressBookService

@Controller
@RequestMapping("/app")
class AppController(@Autowired val service: AddressBookService) {

    @GetMapping(value = ["/add"])
    fun addEntry(model: Model): String {
        model.addAttribute("entry", AddressBookEntry())
        return "addEntry"
    }

    @PostMapping(value = ["/add"])
    fun addEntry(
        @ModelAttribute("entry") entry: AddressBookEntry,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "addEntry"
        }
        service.addEntry(entry)
        return "redirect:/app/list"
    }

    @GetMapping(value = ["/list"])
    fun listEntries(
        @RequestParam(required = false) firstname: String?,
        @RequestParam(required = false) lastname: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?,
        model: Model
    ): String {
        model.addAttribute(
            "entries",
            service.getEntries(firstname, lastname, address, phone, email)
        )
        return "list"
    }

    @GetMapping(value = ["/{id}/view"])
    fun viewEntry(@PathVariable id: Long, model: Model): String {
        model.addAttribute("entry", service.getEntryById(id))
        return "view"
    }

    @GetMapping(value = ["/{id}/edit"])
    fun editEntry(@PathVariable id: Int, model: Model): String {
        model.addAttribute("entry", service.getEntryById(id.toLong()))
        return "edit"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editEntry(
        @PathVariable id: Int,
        @ModelAttribute("entry") entry: AddressBookEntry,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "edit"
        }
        service.updateEntry(entry)
        return "redirect:/app/list"
    }

    @GetMapping(value = ["/{id}/delete"])
    fun deleteEntry(@PathVariable id: Int, model: Model): String {
        service.deleteEntryById(id.toLong())
        return "redirect:/app/list"
    }
}