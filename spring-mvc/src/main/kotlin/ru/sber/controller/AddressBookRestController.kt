package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.sber.data.Contact
import ru.sber.service.AddressBookService


@RestController
@RequestMapping("/api")
class AddressBookRestController(@Autowired val service : AddressBookService) {

    @GetMapping("/list")
    fun getAddressBook(): Map<Long, Contact>? {
        return service.getAll()
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody contact: Contact) = service.add(contact)

    @GetMapping("/{id}/view")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = service.getById(id)

    @PutMapping("/{id}/edit")
    fun update(@PathVariable id: Long, @RequestBody contact: Contact) =  service.update(id, contact)

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) = service.delete(id)

}