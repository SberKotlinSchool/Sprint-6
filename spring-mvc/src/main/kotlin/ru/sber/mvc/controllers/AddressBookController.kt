package ru.sber.mvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.mvc.domain.DomainRecord
import ru.sber.mvc.services.AddressBookService

@Controller
@RequestMapping("/app")
class AddressBookController @Autowired constructor(
    private val addressBookService: AddressBookService
) {

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun add(model: Model): String {
        model["newRecord"] = DomainRecord.EMPTY
        return "add"
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun add(newRecord: DomainRecord): String {
        val id = addressBookService.add(newRecord)

        return "redirect:$id/view"

    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun list(model: Model): String {
        model["records"] = addressBookService.getAll()

        return "list"
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun view(@PathVariable id: Long, model: Model): String {
        model["record"] = addressBookService.getById(id)

        return "view"
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun edit(@PathVariable id: Long, model: Model): String {
        model["newRecord"] = addressBookService.getById(id)

        return "edit"
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun edit(@PathVariable id: Long, newRecord: DomainRecord, model: Model): String {
        addressBookService.editById(id, newRecord)

        return "redirect:/app/$id/view"
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasPermission(#id, 'ru.sber.mvc.data.Record','DELETE')")
    fun delete(@PathVariable id: Long): String {
        addressBookService.delete(id)

        return "redirect:/app/list"
    }
}
