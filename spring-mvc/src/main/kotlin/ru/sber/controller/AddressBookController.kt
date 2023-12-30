package ru.sber.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.service.AddressBookService
import ru.sber.model.Person
import java.util.concurrent.atomic.AtomicLong

@Controller
@RequestMapping("/app")
class AddressBookController(private val addressBookService: AddressBookService) {
    private val atomicId = AtomicLong(1)

    @GetMapping("/add")
    fun getFormForAdd(model: Model): String {
        model.addAttribute("entity", Person(atomicId.incrementAndGet(), "", "", ""))
        return "updatePerson"
    }

    @PostMapping("/add")
    fun addNewPerson(@ModelAttribute person: Person): String {
        addressBookService.addNewPerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun showAllPersons(model: Model): String {
        model.addAttribute("addressBook", addressBookService.getAllPersons())
        return "allPersons"
    }

    @GetMapping("/{id}/view")
    fun showPersonById(@PathVariable id: Long, model: Model): String {
        model.addAttribute("id", id)
        model.addAttribute("entity", addressBookService.getPersonById(id))
        return "showPerson"
    }

    @GetMapping("/{id}/edit")
    fun getFormForEdit(@PathVariable id: Long, model: Model): String {
        model.addAttribute("id", id)
        model.addAttribute("entity", addressBookService.getPersonById(id))
        return "updatePerson"
    }

    @PostMapping("/{id}/edit")
    fun updatePersonById(@PathVariable id: Long, @ModelAttribute("entity") person: Person): String {
        addressBookService.updatePersonInfo(id, person)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deletePersonById(@PathVariable id: Long): String {
        addressBookService.deletePerson(id)
        return "redirect:/app/list"
    }
}