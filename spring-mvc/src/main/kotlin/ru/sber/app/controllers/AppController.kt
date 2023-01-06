package ru.sber.app.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import ru.sber.app.AddressBook
import ru.sber.app.FormModel
import ru.sber.app.ID

@Controller
@RequestMapping(path=["/app"])
class AppController {
    @Autowired
    private lateinit var addressBook : AddressBook
    private var iD = ID("0") // default parameter

    @PostMapping
    fun appPost(): String {
        return "main_page"
    }

    @GetMapping
    fun appGet(): String {
        return "main_page"
    }

    @PostMapping("/add")
    fun appAdd(@ModelAttribute form: FormModel): String {
        if (form.firstName != null || form.lastName != null || form.city != null) {
            addressBook.addValue(FormModel(form.firstName, form.lastName, form.city))
        }
        return "addition"
    }

    @PostMapping("/list")
    fun appListPost(model: Model): String {
        model["data"] = addressBook.getFields()
        return "book"
    }

    @GetMapping("/list")
    fun appListGet(model: Model): String {
        model["data"] = addressBook.getFields()
        return "book"
    }

    @PostMapping("/field_setting")
    fun listSettingPost(@ModelAttribute form: ID, model: Model): String {
        if(form.id.isEmpty()) { form.id = addressBook.getFields().keys.first().toString() }
        model["id"] = form.id
        iD.id = form.id
        return "setting_menu"
    }

    @GetMapping("/field_setting")
    fun listSettingGet(model: Model): String {
        model["id"] = iD.id
        return "setting_menu"
    }

    @PostMapping("/{id}/view")
    fun viewApp(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("id", id)
        model["data"] = addressBook.getValue(id)
        return "field"
    }

    @PostMapping("/{id}/edit")
    fun editApp(@PathVariable("id") id: Int, @ModelAttribute form: FormModel, model: Model): String {
        model.addAttribute("id", id)
        if (form.firstName != null || form.lastName != null || form.city != null) {
            addressBook.addValue(iD.id.toInt(), FormModel(form.firstName, form.lastName, form.city))
        }
        return "editor"
    }

    @PostMapping("/{id}/delete")
    fun deleteApp(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("id", id)
        addressBook.deleteValue(id)
        return "del"
    }
}