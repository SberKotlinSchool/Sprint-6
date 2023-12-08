package com.example.adresbook.controller

import com.example.adresbook.model.BookRecord
import com.example.adresbook.service.AddressBookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/app/v1")
class AddressBookController(private val addressBookService: AddressBookService) {
    @GetMapping("/add")
    fun add(model: Model): String {
        model.addAttribute("record", BookRecord())
        return "add"
    }

    @PostMapping("/add")
    fun add(bookRecord: BookRecord): String {
        println(bookRecord)
        addressBookService.addBookRecord(bookRecord)
        return "redirect:/app/v1/list"
    }

    @GetMapping("/list")
    fun list(
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        model: Model
    ): String {
        model.addAttribute("records", addressBookService.getList(address, phone))
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(model: Model, @PathVariable id: Long): String {
        model.addAttribute("record", addressBookService.getBookRecord(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(model: Model, @PathVariable id: Long): String {
        model.addAttribute("record", addressBookService.getBookRecord(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(bookRecord: BookRecord, @PathVariable id: Int): String {
        addressBookService.editBookRecord(bookRecord, id)
        return "redirect:/app/v1/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        addressBookService.deleteBookRecord(id)
        return "redirect:/app/v1/list"
    }
}