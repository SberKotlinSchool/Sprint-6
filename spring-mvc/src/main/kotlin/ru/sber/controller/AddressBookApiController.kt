package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.model.Customer
import ru.sber.services.AddressBookService
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AddressBookApiController @Autowired constructor(val service: AddressBookService) {


    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<Customer> {
        service.add(customer)
        return ResponseEntity.ok(customer)
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun getCustomers(
        @RequestParam(required = false) fio: String?,
        model: Model
    ): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(service.getAll(fio))
    }

    @RequestMapping(value = ["/{id}/view"], method = [RequestMethod.GET])
    fun viewCustomer(@PathVariable id: Int): ResponseEntity<Customer?> {
        return ResponseEntity.ok(service.getById(id))
    }

    @RequestMapping(value = ["/{id}/edit"], method = [RequestMethod.PUT])
    fun editCustomer(
        @PathVariable id: Int,
        @Valid @RequestBody customer: Customer,
        result: BindingResult
    ): ResponseEntity<Customer?> {
        service.update(customer)
        return ResponseEntity.ok(customer)
    }

    @RequestMapping(value = ["/{id}/delete"], method = [RequestMethod.DELETE])
    fun deleteCustomer(@PathVariable id: Int) {
        service.delete(id)
    }
}
