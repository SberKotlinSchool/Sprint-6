package ru.sber.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.model.Person
import ru.sber.service.AddressBookService

@Controller
@RequestMapping("/app")
class AddressBookController(private val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addNewPerson(person: Person): String {
        addressBookService.addNewPerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun showAllPersons(model: Model): String {
        model.addAttribute("addressBook", addressBookService.getAllPersons())
        return "list"
    }

    @GetMapping("{id}/view")
    fun showPersonById(@PathVariable id: Long, model: Model): String {
        model.addAttribute("id", id)
        model.addAttribute("person", addressBookService.getPersonById(id))
        return "show_person"
    }

    @PostMapping("{id}/edit")
    fun updatePersonById(@PathVariable id: Long, @ModelAttribute("person") person: Person): String {
        addressBookService.updatePersonInfo(id, person)
        return "edit"
    }

    @GetMapping("{id}/delete")
    fun deletePersonById(@PathVariable id: Long): String {
        addressBookService.deletePerson(id)
        return "delete_person"
    }
}