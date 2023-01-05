package ru.sber.mvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.mvc.data.AddressBookRepository
import ru.sber.mvc.domain.Record

@Controller
@RequestMapping("/app")
class AddressBookController @Autowired constructor(
    private val addressBook: AddressBookRepository
) {

    @GetMapping("/add")
    fun add(model: Model): String {
        model["newRecord"] = Record.EMPTY
        return "add"
    }

    @PostMapping("/add")
    fun add(newRecord: Record): String {
        val id = addressBook.add(newRecord)

        return "redirect:$id/view"

    }

    @GetMapping("/list")
    fun list(model: Model): String {
        model["records"] = addressBook.getAll()

        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String, model: Model): String {
        model["record"] = addressBook.getById(id)

        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String, model: Model): String {
        model["newRecord"] = addressBook.getById(id)

        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: String, newRecord: Record, model: Model): String {
        addressBook.editById(id, newRecord)

        return "redirect:/app/$id/view"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: String): String {
        addressBook.delete(id)

        return "redirect:/app/list"
    }
}
