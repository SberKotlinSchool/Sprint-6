package ru.sber.mvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sber.mvc.data.AddressBookRepository
import ru.sber.mvc.domain.Record

@RestController
@RequestMapping("api")
class RestAddressBookController @Autowired constructor(
    private
    val addressBook: AddressBookRepository
) {

    @PostMapping("/add")
    fun add(@RequestBody newRecord: Record): Map<String, String> =
        mapOf("id" to addressBook.add(newRecord))

    @GetMapping("/list")
    fun list(): Map<String, List<Record>> =
        mapOf("records" to addressBook.getAll())

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String): Map<String, Record> =
        mapOf("record" to addressBook.getById(id))

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: String, @RequestBody newRecord: Record): Map<String, Record> =
        mapOf("newRecord" to addressBook.editById(id, newRecord))

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: String) {
        addressBook.delete(id)
    }
}
