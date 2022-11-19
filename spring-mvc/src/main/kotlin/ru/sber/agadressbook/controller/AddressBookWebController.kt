package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService

@RequestMapping("/addressbook")
@Controller
class AddressBookWebController @Autowired constructor(
    val addressBookWebService: AddressBookWebService) {

    @GetMapping("/record/{id}")
    fun getPerson(@PathVariable("id") id: Int, model: Model): String {
        addressBookWebService.getRecordById(id)?.firstName?.let { model["firstName"] = it }
        model["firstName"] = addressBookWebService.getRecordById(id)?.firstName!!
        return "test"
    }

    @GetMapping("")
    fun index(): String {
        return "redirect:/addressbook/list"
    }

    @GetMapping("/add")
    fun addRecord(): String {
        return "add_record"
    }

    @PostMapping("/add")
    fun create(@ModelAttribute("record") person: Person?): String? {
        addressBookWebService.addRecord(person!!)
        return "redirect:/addressbook/list"
    }

    @GetMapping("{id}/edit")
    fun editRecord(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("record", addressBookWebService.getRecordById(id))
        return "edit_record"
    }

    @PatchMapping("{id}/edit")
    fun updateRecord(@ModelAttribute("record") person: Person?, @PathVariable("id") id: Int): String {
        addressBookWebService.updateRecord(id, person!!)
        return "redirect:/addressbook/list"
    }


    @GetMapping("list")
    fun getAllRecord(model: Model): String {

        model.addAttribute("records", addressBookWebService.getAllRecords())
        return "list_records"
    }


    @DeleteMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Int): String? {
        addressBookWebService.deleteRecord(id)
        return "redirect:/addressbook/list"
    }

    @GetMapping("{id}/view")
    fun viewRecord(@PathVariable("id") id: Int, model: Model): String {
        model["record"] = addressBookWebService.getRecordById(id)!!
        return "view_record"
    }
}

