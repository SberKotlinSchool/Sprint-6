package ru.sbrf.zhukov.springmvc.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sbrf.zhukov.springmvc.data.AddressEntry
import ru.sbrf.zhukov.springmvc.service.AddressBookService

@Controller
@RequestMapping("/app")
class AddressBookController(
    private val addressBookService: AddressBookService
) {

    @GetMapping("/list")
    fun listEntries(model: Model): String {
        val entries = addressBookService.getAllEntries()
        model.addAttribute("entries", entries)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewEntry(@PathVariable id: Long, model: Model): String {
        val entry = addressBookService.getEntry(id)
        if (entry != null) {
            model.addAttribute("entry", entry)
            return "view"
        } else {
            return "redirect:/app/list"
        }
    }

    @GetMapping("/add")
    fun showAddForm(model: Model): String {
        model.addAttribute("entry", AddressEntry(0, "", ""))
        return "edit"
    }

    @PostMapping("/add")
    fun addEntry(@ModelAttribute entry: AddressEntry): String {
        addressBookService.addEntry(entry)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/edit")
    fun showEditForm(@PathVariable id: Long, model: Model): String {
        val entry = addressBookService.getEntry(id)
        if (entry != null) {
            model.addAttribute("entry", entry)
            return "edit"
        } else {
            return "redirect:/app/list"
        }
    }

    @PostMapping("/{id}/edit")
    fun editEntry(@PathVariable id: Long, @ModelAttribute entry: AddressEntry): String {
        addressBookService.editEntry(id, entry)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deleteEntry(@PathVariable id: Long): String {
        addressBookService.deleteEntry(id)
        return "redirect:/app/list"
    }
}
