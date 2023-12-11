package ru.sber.springmvc.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.springmvc.domain.Person
import ru.sber.springmvc.service.PersonService

@Controller
@RequestMapping("/app")
class AppController(private val personService: PersonService) {
    @GetMapping("/list")
    fun getAll(@RequestAttribute(required = false) name: String?, model: Model): String {
        if (name == null) {
            model.addAttribute("persons", personService.getAll())
        } else {
            model.addAttribute("persons", personService.getByName(name))
        }
        return "list"
    }

    @GetMapping("/person/{id}")
    fun getPerson(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", personService.getById(id))
        return "person"
    }

    @GetMapping("/person/add")
    fun getAddView(model: Model): String {
        model.addAttribute("person", Person())
        return "add"
    }

    @PostMapping("/person/add")
    fun addNewPerson(@ModelAttribute("person") person: Person): String {
        personService.addPerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/person/{id}/edit")
    fun getEditView(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", personService.getById(id))
        return "edit"
    }

    @PostMapping("/person/{id}/edit")
    fun editPerson(@PathVariable id: Int, @ModelAttribute("person") person: Person): String {
        personService.updatePerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/person/{id}/delete")
    fun deletePerson(@PathVariable id: Int): String {
        personService.deletePerson(id)
        return "redirect:/app/list"
    }
}