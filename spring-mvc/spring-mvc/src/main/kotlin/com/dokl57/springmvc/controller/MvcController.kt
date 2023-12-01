package com.dokl57.springmvc.controller

import com.dokl57.springmvc.model.Entry
import com.dokl57.springmvc.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class MvcController
@Autowired constructor(private val addressBookService: AddressBookService) {
    val MAIN_PAGE = "redirect:/app/list"

    @GetMapping("/add")
    fun showAddEntry(model: Model): String {
        model.addAttribute("entry", Entry("", ""))
        return MAIN_PAGE
    }

    @PostMapping("/add")
    fun addEntry(@ModelAttribute("entry") entry: Entry): String {
        addressBookService.addEntry(entry)
        return MAIN_PAGE
    }

    @GetMapping("/list")
    fun getEntries(@RequestParam(required = false) query: String?, model: Model): String {
        model.addAttribute("entries", addressBookService.getEntries(query))
        return "list"
    }

    @GetMapping("{id}/view")
    fun viewEntry(@PathVariable id: Long, model: Model): String {
        model.addAttribute("entry", addressBookService.findEntryById(id) ?: return MAIN_PAGE)
        return "view"
    }

    @GetMapping("{id}/edit")
    fun showEditEntry(@PathVariable id: Long, model: Model): String {
        model.addAttribute("entry", addressBookService.findEntryById(id) ?: return MAIN_PAGE)
        model.addAttribute("entryId", id)
        return "edit"
    }

    @PostMapping("{id}/edit")
    fun editEntry(@PathVariable id: Long, @ModelAttribute("entry") entry: Entry): String {
        addressBookService.editEntry(id, entry)
        return MAIN_PAGE
    }

    @GetMapping("/{id}/delete")
    fun deleteEntry(@PathVariable id: Long): String {
        addressBookService.deleteEntry(id)
        return MAIN_PAGE
    }
}
