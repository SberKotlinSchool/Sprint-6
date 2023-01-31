package ru.sber.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import ru.sber.dto.Address
import ru.sber.repository.AddressBookRepository

@RestController
@RequestMapping("/api")
class RestController(
    private val addressBookRepository: AddressBookRepository
) {
    @PostMapping("/add")
    fun add(@RequestBody address: Address) = ResponseEntity.ok(addressBookRepository.add(address))

    @GetMapping("/list")
    fun list(@RequestParam(required = false) query: String?) = ResponseEntity.ok(
        if (query.isNullOrEmpty())
            addressBookRepository.getAll()
        else {
            addressBookRepository.getAll().filter { it.value.address == query }
        }
    )

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: Long) = ResponseEntity.ok(
        addressBookRepository.get(id)
    )

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody address: Address) = ResponseEntity.ok(
        addressBookRepository.update(id, address)
    )

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) = ResponseEntity.ok(
        addressBookRepository.delete(id)
    )
}