package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService


@RequestMapping("/addressbook")
@Controller
class AddressBookWebController @Autowired constructor(val addressBookWebService: AddressBookWebService) {

    @GetMapping("/record/{id}")
    fun getPerson(@PathVariable("id") id: Int, model: Model): String {
        addressBookWebService.getRecordId(id)?.firstName?.let { model["firstName"] = it }
        model["firstName"] = addressBookWebService.getRecordId(id)?.firstName!!
        return "test"
    }

    @GetMapping("/")
    fun index(): String {
        return "redirect:/addressbook/all"
    }

    @GetMapping("login_form")
    fun login(): String {
        return "login_form"
    }

    @GetMapping("add")
    fun addRecord(): String {
        return "add_record"
    }

    @GetMapping("{id}/edit")
    fun editRecord(@PathVariable("id") id: Int, model: Model): String {
        println("AAA ${addressBookWebService.getRecordId(id)}")
        model["record"] = addressBookWebService.getRecordId(id)!!
        return "edit_record"
    }

    @PostMapping("{id}/edit")
    fun updateRecord(@ModelAttribute("record") person: Person?, @PathVariable("id") id: Int): String {
        addressBookWebService.addRecord(id, person!!)
        return "redirect:/addressbook/all"
    }

    @GetMapping("delete")
    fun deleteRecord(): String {
        return "delete_record"
    }

    @GetMapping("all")
    fun getAllRecord(model: Model): String {
        model.addAttribute("records", addressBookWebService.getAllRecords())
        return "all_records"
    }


}