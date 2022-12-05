package com.sbuniver.homework

import com.sbuniver.homework.dto.AddressBook
import com.sbuniver.homework.dto.AddressDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ApiController {

    @Autowired
    lateinit var addressBook: AddressBook

    @GetMapping("/list")
    fun list() = addressBook.addresses

    @PostMapping("/add")
    fun add(@RequestBody address: AddressDto) = addressBook.add(address)

    @GetMapping("/{id}/view")
    fun getById(@PathVariable id: Int) = addressBook.get(id)

    @PutMapping("/edit")
    fun editById(@RequestBody address: AddressDto) = addressBook.update(address)

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Int) = addressBook.delete(id)
}


