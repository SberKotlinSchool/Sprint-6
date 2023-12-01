package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import ru.sber.dto.Address
import ru.sber.repository.AddressBook

@Controller
@RequestMapping("/app")
class MvcController @Autowired constructor( val addressBook: AddressBook) {

    @GetMapping("/list")
    fun view(@RequestParam(required = false) name: String?, model: Model): String {
        val result = if( name.isNullOrBlank() ) addressBook.getAll()
        else addressBook.getAll().filter { it.second.name == name }
        model.addAttribute("address",  result )
        return "list"
    }

    @GetMapping("/add")
    fun add(model: Model): String{
        model.addAttribute("address", Address())
        return "add"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute address: Address): String {
        addressBook.add(address)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Int): String {
        addressBook.delete(id)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, model: Model): String{
        model.addAttribute("address", addressBook.getById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Int, @ModelAttribute address: Address): String {
        addressBook.update(id, address)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/view")
    fun detail(@PathVariable id: Int, model: Model): String{
        model.addAttribute("address", addressBook.getById(id))
        return "view"
    }

}