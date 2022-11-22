package ru.sber.ufs.cc.kulinich.springmvc.controllers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sber.ufs.cc.kulinich.springmvc.filters.LoggingFilter
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel
import ru.sber.ufs.cc.kulinich.springmvc.services.AddressBookService


@RestController
@RequestMapping("/api/*")
class RESTController {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    lateinit var addrBook : AddressBookService
        @Autowired set

    @PostMapping("/add")
    fun addCustomer( @RequestBody form: AddressBookModel): ResponseEntity<AddressBookModel> {
        addrBook.add(form)
        return ResponseEntity.ok(form)
    }

    @GetMapping( "/list")
    fun getCustomers(model: Model): ResponseEntity<List<AddressBookModel>> {
        return ResponseEntity.ok(addrBook.getAll())
    }

    @GetMapping("/{id}/view")
    fun viewCustomer(@PathVariable id: Int): ResponseEntity<AddressBookModel?> {
        return ResponseEntity.ok(addrBook.getById(id))
    }

    @PostMapping("/{id}/edit")
    fun editCustomer(
        @PathVariable id: Int,
        @RequestBody form: AddressBookModel,
        result: BindingResult
    ): ResponseEntity<AddressBookModel?> {
        addrBook.edit(id, form)
        return ResponseEntity.ok(form)
    }

    @PostMapping("/{id}/delete")
    fun deleteCustomer(@PathVariable id: Int) {
        addrBook.delete(id)
    }

}