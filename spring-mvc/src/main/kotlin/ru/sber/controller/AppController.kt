package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.model.Note
import ru.sber.repository.AddressBookRepository

@Controller
@RequestMapping("/app")
class AppController(@Autowired val addressBookRepository: AddressBookRepository) {

    @GetMapping("/list")
    fun list(
        @RequestParam name: String?,
        @RequestParam address: String?,
        @RequestParam phone: String?,
        model: Model,
    ): String {
        model.addAttribute("notes", addressBookRepository.searchWithFilter(name, address, phone))
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Long, model: Model): String {
        model.addAttribute("note", addressBookRepository.getById(id))
        return "view"
    }

    @GetMapping("/add")
    fun addView(@RequestBody note: Note?, model: Model): String {
        model.addAttribute("note", note)
        return "add"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute("note") note: Note): String {
        addressBookRepository.create(note)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/edit")
    fun editView(@PathVariable id: Long, model: Model): String {
        model.addAttribute("note", addressBookRepository.getById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @ModelAttribute("note") note: Note, model: Model): String {
        model.addAttribute("note", note)
        addressBookRepository.updateById(id, note)
        return "redirect:/app/$id/view"
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Long, model: Model): String {
        addressBookRepository.deleteById(id)
        return "redirect:/app/list"
    }
}
