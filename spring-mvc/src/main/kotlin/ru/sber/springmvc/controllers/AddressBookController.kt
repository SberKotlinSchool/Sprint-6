package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record

@Controller
@RequestMapping("/app")
class AddressBookController @Autowired constructor(val addressBook: AddressBookRepository) {

    @GetMapping("/add")
    fun addRecord(): String {
        return "newRecord"
    }

    @PostMapping("/add")
    fun addRecord(@ModelAttribute("record") record: Record): String {
        addressBook.addRecord(record)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun showAll(model: Model): String {
        model.addAttribute("records", addressBook.getAll())
        return "showAll"
    }

    @GetMapping("/{id}/view")
    fun getById(@PathVariable("id") id: Long, model: Model): String? {
        model.addAttribute("recordById", addressBook.getById(id))
        return "getById"
    }

    @GetMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Long, model: Model): String {
        addressBook.deleteById(id)
        return "redirect:/app/list"
    }

    @GetMapping("{id}/edit")
    fun editGet(model: Model, @PathVariable("id") id: Long): String {
        model.addAttribute("record", addressBook.getById(id))
        return "edit"
    }

    @PostMapping("{id}/edit")
    fun editPost(@ModelAttribute("record") record: Record, @PathVariable("id") id: Long): String {
        addressBook.updateRecord(id, record)
        return "redirect:/app/list"
    }
}