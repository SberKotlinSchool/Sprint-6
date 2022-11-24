package ru.sber.kotlinmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.kotlinmvc.entities.Client
import ru.sber.kotlinmvc.services.ClientRepository

@Controller
@RequestMapping("/addressBook")
class MvcController @Autowired constructor(val repo : ClientRepository) {

    @GetMapping("/list")
    fun getList(@RequestParam(required = false) name: String?,
                @RequestParam(required = false) address: String?,
                @RequestParam(required = false) phone: String?,
                @RequestParam(required = false) email: String?,
                model : Model) : String {
        val clients = repo.find(name, address, phone, email)
        model.addAttribute("clients", clients)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun getClient(@PathVariable("id") id : Integer, model : Model) : String {
        repo.findById(id).ifPresent {model.addAttribute("client", it)}
        return "view"
    }

    @GetMapping("/add")
    fun initAddClient(model: Model): String {
        model.addAttribute("client", Client("", null, null, null))
        return "add"
    }

    @PostMapping("/add")
    fun submitAddClient(@ModelAttribute("client") client: Client, result: BindingResult): String {
        if (result.hasErrors()) {
            return "add"
        }
        repo.save(client)
        return "redirect:/addressBook/list"
    }

    @GetMapping("/{id}/edit")
    fun initEditClient(@PathVariable("id") id : Integer, model : Model) : String {
        repo.findById(id).ifPresent {model.addAttribute("client", it)}
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun submitEditClient(@ModelAttribute("client") client: Client, result: BindingResult): String {
        repo.save(client)
        return "redirect:/addressBook/list"
    }

    @GetMapping("/{id}/delete")
    fun deleteClient(@PathVariable id: Integer, model: Model): String {
        repo.delete(id)
        return "redirect:/addressBook/list"
    }
}