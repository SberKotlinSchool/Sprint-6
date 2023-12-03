package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.model.BaseEntity
import ru.sber.service.AddressBookService

@Controller
@RequestMapping("/app")
class MvcController @Autowired constructor(private val addressBookService: AddressBookService) {
    val TO_MAIN_PAGE = "redirect:/app/list"

    @GetMapping("/add")
    fun showAddBaseEntity(model: Model): String {
        model.addAttribute("baseEntity", BaseEntity("", ""))
        return "add"
    }

    @PostMapping("/add")
    fun addBaseEntity(@ModelAttribute("baseEntity") baseEntity: BaseEntity): String {
        addressBookService.addBaseEntity(baseEntity)
        return TO_MAIN_PAGE
    }

    @GetMapping("/list")
    fun getbaseEntities(@RequestParam(required = false) query: String?, model: Model): String {
        model.addAttribute("baseEntities", addressBookService.getBaseEntities(query))
        return "list"
    }

    @GetMapping("{id}/view")
    fun viewBaseEntity(@PathVariable id: Long, model: Model): String {
        model.addAttribute("baseEntity", addressBookService.findBaseEntityById(id) ?: return TO_MAIN_PAGE)
        return "view"
    }

    @GetMapping("{id}/edit")
    fun showEditBaseEntity(@PathVariable id: Long, model: Model): String {
        model.addAttribute("baseEntity", addressBookService.findBaseEntityById(id) ?: return TO_MAIN_PAGE)
        model.addAttribute("baseEntityId", id)
        return "edit"
    }

    @PostMapping("{id}/edit")
    fun editBaseEntity(@PathVariable id: Long, @ModelAttribute("baseEntity") baseEntity: BaseEntity): String {
        addressBookService.editBaseEntity(id, baseEntity)
        return TO_MAIN_PAGE
    }

    @GetMapping("/{id}/delete")
    fun deleteBaseEntity(@PathVariable id: Long): String {
        addressBookService.deleteBaseEntity(id)
        return TO_MAIN_PAGE
    }
}