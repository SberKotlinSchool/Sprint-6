package ru.sber.springmvc.controller.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.model.Person
import ru.sber.springmvc.service.PersonService

@Controller
@RequestMapping(value = ["/app"])
class PersonAppController @Autowired constructor(private val personService: PersonService) {

    @GetMapping("/add")
    fun add(model: Model): String {
        model.addAttribute("person", Person())
        return "add"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute("customer") person: Person): String {
        personService.add(person)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun getAll(@RequestParam(required = false) name: String?, model: Model): String {
        if (name == null) {
            model.addAttribute("persons", personService.getAll())
        } else {
            model.addAttribute("persons", personService.getAllContains(name))
        }
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", personService.getById(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, model: Model): String {
        model.addAttribute("person", personService.getById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @ModelAttribute("person") person: Person): String {
        personService.update(person)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Int, model: Model): String {
        personService.delete(id)
        return "redirect:/app/list"
    }


}