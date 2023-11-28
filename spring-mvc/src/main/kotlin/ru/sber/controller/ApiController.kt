package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.sber.model.Note
import ru.sber.repository.AddressBookRepository

@RestController
@RequestMapping("/api")
class ApiController(@Autowired val addressBookRepository: AddressBookRepository) {

    @GetMapping("/list")
    fun list(@RequestParam name: String?, @RequestParam address: String?, @RequestParam phone: String?): List<Note> {
        return addressBookRepository.searchWithFilter(name, address, phone)
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Long): Note? {
        return addressBookRepository.getById(id)
    }

    @PostMapping("/add")
    fun add(@RequestBody note: Note) {
        addressBookRepository.create(note)
    }

    @PutMapping("/{id}/edit")
    fun edit(@RequestBody note: Note, @PathVariable id: Long) {
        addressBookRepository.updateById(id, note)
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) {
        addressBookRepository.deleteById(id)
    }
}