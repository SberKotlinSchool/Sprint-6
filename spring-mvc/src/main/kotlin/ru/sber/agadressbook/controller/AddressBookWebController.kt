package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService

@RequestMapping("/address-book")
@Controller
class AddressBookWebController @Autowired constructor(val addressBookWebService: AddressBookWebService) {

    @GetMapping("/person/{id}")
    fun getPerson(@PathVariable("id") id : Int, model : Model) : String {
        addressBookWebService.getPersonById(id)?.firstName?.let { model["firstName"] = it }
        model["firstName"] = addressBookWebService.getPersonById(id)?.firstName!!
        model["title"] = "This is a address-book title"
        return "test"
    }

    @GetMapping("/")
    fun index() : String {
        return "redirect:/address-book/login_form"
    }

    @GetMapping("login_form")
    fun login() : String {
        return "login_form"
    }

    @GetMapping("add_record")
    fun addRecord() : String {
        return "add_record"
    }

    @GetMapping("edit_record")
    fun editRecord() : String {
        return "edit_record"
    }

    @GetMapping("delete_record")
    fun deleteRecord() : String {
        return "delete_record"
    }

    @GetMapping("all_records")
    fun getAllRecord() : String {
        return "all_records"
    }



}