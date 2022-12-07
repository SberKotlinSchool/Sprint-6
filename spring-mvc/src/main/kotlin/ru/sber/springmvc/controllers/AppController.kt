package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.dto.Entry
import ru.sber.springmvc.services.AppService

@Controller
@RequestMapping("app")
class AppController @Autowired constructor(val service: AppService) {

    @GetMapping("/add")
    fun add() = "add"

    @PostMapping("/add")
    fun add(@ModelAttribute("entry") entry: Entry): String {
        service.add(entry)

        return "redirect:/app/list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String, model: Model): String {
        model.addAttribute("entry", service.view(id))

        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String, model: Model): String {
        return service.view(id)?.let {
            model.addAttribute("entry", it)

            "edit"
        } ?: "redirect:/app/list"
    }

    @PostMapping("/{id}/edit")
    fun edit(@ModelAttribute("entry") entry: Entry, @PathVariable id: String): String {
        service.edit(id, entry)

        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: String, model: Model): String {
        return service.view(id)?.let {
            "delete"
        } ?: "redirect:/app/list"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: String): String {
        service.delete(id)

        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun list(@RequestParam(required = false) query: String?, model: Model): String {
        model.addAttribute("list", service.list(query))

        return "list"
    }
}