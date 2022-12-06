package ru.sber.addresses.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.addresses.dto.Address
import ru.sber.addresses.requests.CreateAddressRq
import ru.sber.addresses.requests.UpdateAddressRq
import ru.sber.addresses.services.AddressService

@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ApiController(
    private val addressService: AddressService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createAddress(@RequestBody rq: CreateAddressRq) =
        addressService.createAddress(rq)
            .let { address -> ResponseEntity.ok(address.id) }

    @GetMapping("{id}/view")
    fun getAddress(@PathVariable("id") id: Long) =
        ResponseEntity.ok(addressService.getAddresses(id))

    @GetMapping("view")
    fun getAddresses() =
        ResponseEntity.ok(addressService.getAddresses())

    @PutMapping("{id}/edit")
    fun updateAddress(@PathVariable("id") id: Long, @RequestBody rq: UpdateAddressRq): ResponseEntity<Address> {
        val result = addressService.updateAddress(id, rq.address)
        return if (result != null) {
            ResponseEntity.ok(result)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("{id}/delete")
    fun deleteAddress(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        val result = addressService.deleteAddress(id)
        return if (result != null) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}