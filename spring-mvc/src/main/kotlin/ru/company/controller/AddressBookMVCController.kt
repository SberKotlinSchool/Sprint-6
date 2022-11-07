package ru.company.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.company.model.Client
import ru.company.service.AddressBookService
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/app"])
class AddressBookMVCController @Autowired constructor(val service: AddressBookService) {

    @GetMapping("/add")
    fun addClient(model: Model): String {
        model.addAttribute("client", Client())
        return "add"
    }

    @PostMapping("/add")
    fun addClient(@Valid @ModelAttribute("client") client: Client, result: BindingResult): String {
        if (result.hasErrors()) {
            return "add"
        }
        service.add(client)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun getClients(
        @RequestParam(required = false) fio: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?,
        model: Model
    ): String {
        val clients = service.getClients(fio, address, phone, email)
        model.addAttribute("clients", clients)

        return "list"
    }

    @GetMapping("/{id}/view")
    fun viewClient(@PathVariable id: Int, model: Model): String {
        model.addAttribute("client", service.getClientById(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun editClient(@PathVariable id: Int, model: Model): String {
        model.addAttribute("client", service.getClientById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editClient(@Valid @ModelAttribute("client") client: Client, result: BindingResult): String {
        if (result.hasErrors()) {
            return "edit"
        }
        service.updateClient(client)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deleteClient(@PathVariable id: Int, model: Model): String {
        service.deleteClient(id)
        return "redirect:/app/list"
    }

}