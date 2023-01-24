package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import ru.sber.service.AddressBookService
import ru.sber.model.AddressBookRecord

@Controller
@RequestMapping("/app")
class MvcController @Autowired constructor(
    private val addressBookService: AddressBookService
) {

    @GetMapping
    fun getDefaultPage(): RedirectView {
        return RedirectView("/")
    }

    @GetMapping("/add")
    fun getAddForm(): String {
        return "create"
    }

    @PostMapping("/add")
    fun addPage(
        @ModelAttribute record: AddressBookRecord,
        model: Model
    ): RedirectView {
        addressBookService.create(record)
        model.addAttribute("result", "Page was Created")
        return RedirectView("/")
    }

    @GetMapping("/list")
    fun getPage(
        @RequestParam query: Map<String, String>?,
        model: Model
    ): String {
        model.addAttribute(
            "result",
            addressBookService.get(query),
        )
        return "list"
    }

    @GetMapping("/{id}/edit")
    fun getEditForm(@PathVariable id: String, model: Model): String {
        val record = addressBookService.get(id.toLong())
        model.addAttribute("name", record.name)
        model.addAttribute("address", record.address)
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editPage(
        @PathVariable id: String,
        @ModelAttribute personPage: AddressBookRecord,
        model: Model
    ): RedirectView {
        addressBookService.update(id.toLong(), personPage)
        model.addAttribute("result", "Page was Edited")
        return RedirectView("/")
    }

    @GetMapping("/{id}/delete")
    fun deletePage(
        @PathVariable id: String,
        model: Model
    ): RedirectView {
        addressBookService.delete(id.toLong())
        model.addAttribute("result", "Page was Deleted")
        return RedirectView("/")
    }
}
