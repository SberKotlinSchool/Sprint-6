package ru.sber.addressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.addressbook.model.Person
import ru.sber.addressbook.service.PersonService

@Controller
@RequestMapping("/persons")
class PersonController @Autowired constructor(val personService: PersonService) {

    @GetMapping
    fun getPersonList(model: Model): String {
        model.addAttribute("persons", personService.getAll())
        return "persons"
    }

    @GetMapping("/add")
    fun add(model: Model): String {
        model.addAttribute("person", Person())
        return "add"
    }

    @PostMapping("/add")
    fun add(person: Person): String {
        personService.add(person)
        return "redirect:/persons"
    }

    @GetMapping("/{id}/view")
    fun getById(model: Model, @PathVariable id: Long): String {
        model.addAttribute("person", personService.getById(id))
        return "view"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        personService.delete(id)
        return "redirect:/persons"
    }

    @GetMapping("/{id}/edit")
    fun edit(model: Model, @PathVariable id: Long): String {
        model.addAttribute("person", personService.getById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun update(person: Person, @PathVariable id: String): String {
        personService.update(person)
        return "redirect:/persons"
    }
}