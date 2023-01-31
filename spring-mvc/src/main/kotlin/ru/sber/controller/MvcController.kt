package ru.sber.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import ru.sber.dto.Address
import ru.sber.repository.AddressBookRepository

@Controller
@RequestMapping("/app")
class MvcController(private val addressBookRepository: AddressBookRepository) {

    @GetMapping("/add")
    fun addAddress(model: Model): String {
        model.addAttribute("address", Address())
        return "add"
    }

    @PostMapping("/add")
    fun addAddress(@ModelAttribute address: Address): RedirectView {
        addressBookRepository.add(address)
        return RedirectView("list")
    }

    @GetMapping("/list")
    fun listAddress(@RequestParam(required = false) query: String?, model: Model): String {
        val res = if (query.isNullOrEmpty()) addressBookRepository.getAll()
        else {
            addressBookRepository.getAll().filter { it.value.address == query }
        }
        model.addAttribute("addresses", res)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Long, model: Model): String {
        model.addAttribute("address", addressBookRepository.get(id))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        addressBookRepository.get(id)
        model.addAttribute("address", Address())
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @ModelAttribute address: Address): RedirectView {
        addressBookRepository.update(id, address)
        return RedirectView("../list")
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long, model: Model): RedirectView {
        addressBookRepository.delete(id)
        return RedirectView("../list")
    }

}