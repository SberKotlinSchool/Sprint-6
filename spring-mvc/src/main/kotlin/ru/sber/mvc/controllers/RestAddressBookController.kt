package ru.sber.mvc.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sber.mvc.domain.DomainRecord
import ru.sber.mvc.services.AddressBookService

@RestController
@RequestMapping("api")
class RestAddressBookController(private val addressBookService: AddressBookService) {

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_API')")
    fun add(@RequestBody newRecord: DomainRecord): Map<String, Long> =
        mapOf("id" to addressBookService.add(newRecord))

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_API')")
    fun list(): Map<String, List<DomainRecord>> =
        mapOf("records" to addressBookService.getAll())

    @GetMapping("/{id}/view")
    @PreAuthorize("hasRole('ROLE_API')")
    fun view(@PathVariable id: Long): Map<String, DomainRecord> =
        mapOf("record" to addressBookService.getById(id))

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_API')")
    fun edit(@PathVariable id: Long, @RequestBody newRecord: DomainRecord): Map<String, DomainRecord> =
        mapOf("newRecord" to addressBookService.editById(id, newRecord))

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasPermission(#id, 'ru.sber.mvc.data.Record','DELETE')")
    fun delete(@PathVariable id: Long) {
        addressBookService.delete(id)
    }
}
