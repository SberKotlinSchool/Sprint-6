package ru.sber.addressbook.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.sber.addressbook.models.CLientsInfo
import ru.sber.addressbook.services.AddressbookService

@RestController
@RequestMapping("api")
class AddressbookRestController @Autowired constructor(private val service: AddressbookService) {

    @GetMapping("/list")
    fun list(@RequestParam(required = false) query: String?) : List<CLientsInfo> = service.list(query)

    @PostMapping("/add")
    fun add(@RequestBody client: CLientsInfo) = service.add(client)

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) =
        service.view(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @RequestMapping("{id}/edit", method = [RequestMethod.POST])
    fun edit(@PathVariable id: String, @RequestBody client: CLientsInfo) =
        service.edit(id, client)

    @RequestMapping("{id}/delete", method = [RequestMethod.DELETE])
    fun delete(@PathVariable id: String) =
        service.delete(id)
}