package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import ru.sber.agadressbook.models.Credentials
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.repository.AddressBookRepository


@RequestMapping("/addressbook")
@Controller
class AddressBookWebController @Autowired constructor(val addressBookRepository: AddressBookRepository) {

    @GetMapping("/record/{id}")
    fun getPerson(@PathVariable("id") id: Int, model: Model): String {
        addressBookRepository.getRecordById(id)?.firstName?.let { model["firstName"] = it }
        model["firstName"] = addressBookRepository.getRecordById(id)?.firstName!!
        return "test"
    }

    @GetMapping("")
    fun index(): String {
        return "redirect:/addressbook/list"
    }

    @GetMapping("/login_form")
    fun login(): String {
        return "login_form"
    }

    @PostMapping("/login_form")
    fun doLogin(@ModelAttribute("credentials") credentials: Credentials): String {
        val userPassword = "123"
        if(credentials.password == userPassword) {
            return "redirect:/addressbook/list"
        } else {
            return "redirect:/addressbook/login_form"
        }
    }

    @GetMapping("/add")
    fun addRecord(): String {
        return "add_record"
    }

    @PostMapping("/add")
    fun create(@ModelAttribute("record") person: Person?): String? {
        addressBookRepository.addRecord(person!!)
        return "redirect:/addressbook/list"
    }

    @GetMapping("{id}/edit")
    fun editRecord(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("record",addressBookRepository.getRecordById(id))
        return "edit_record"
    }

    @PostMapping("{id}/edit")
    fun updateRecord(@ModelAttribute("record") person: Person?, @PathVariable("id") id: Int): String {
        println("AAA ${person}")
        addressBookRepository.updateRecord(id, person!!)
        return "redirect:/addressbook/list"
    }

    @GetMapping("delete")
    fun deleteRecord(): String {
        return "delete_record"
    }

    @GetMapping("list")
    fun getAllRecord(model: Model): String {
        model.addAttribute("records", addressBookRepository.getAllRecords())
        return "list_records"
    }


    @DeleteMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Int): String? {
        addressBookRepository.deleteRecord(id)
        return "redirect:/addressbook/list"
    }

    @GetMapping("{id}/view")
    fun viewRecord(@PathVariable("id") id: Int, model: Model): String {
        model["record"] = addressBookRepository.getRecordById(id)!!
        return "view_record"
    }


}