package ru.sber.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.sber.model.Person
import ru.sber.service.AddressBookService

@Controller
@RequestMapping(value = ["/app"])
class AddressBookMVCController @Autowired constructor(val service: AddressBookService) {

    @GetMapping("/add")
    fun addPerson(model: Model): String {
        model.addAttribute("person", Person())
        return "add"
    }

    @PostMapping("/add")
    fun addPerson(@ModelAttribute("person") person: Person, result: BindingResult): String {
        if (result.hasErrors()) {
            return "add"
        }
        service.add(person)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun getPerson(
        @RequestParam(required = false) fio: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?,
        model: Model
    ): String {
        val persons = service.getPersons(fio, address, phone, email)
        model.addAttribute("persons", persons)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewPerson(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", service.getPersonById(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun editPerson(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", service.getPersonById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editPerson(@ModelAttribute("person") person: Person, result: BindingResult): String {
        if (result.hasErrors()) {
            return "edit"
        }
        service.updatePerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deletePerson(@PathVariable id: Int, model: Model): String {
        service.deletePerson(id)
        return "redirect:/app/list"
    }

}