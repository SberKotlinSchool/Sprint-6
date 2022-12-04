package ru.sber.addresses.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import ru.sber.addresses.requests.CreateAddressRq
import ru.sber.addresses.requests.DeleteAddressRq
import ru.sber.addresses.requests.GetAddressRq
import ru.sber.addresses.requests.UpdateAddressRq
import ru.sber.addresses.services.AddressService
import java.net.URI

@RestController
@RequestMapping("api")
class ApiController(
    private val addressService: AddressService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createAddress(@RequestBody rq: CreateAddressRq) =
        addressService.createAddress(rq)
            .let { address -> ResponseEntity.created(URI("/app/${address.id}")).build<Unit>() }

    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getAddresses(@RequestBody rq: GetAddressRq) =
        ResponseEntity.ok(addressService.getAddresses(rq.id))

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateAddress(@RequestBody rq: UpdateAddressRq): ResponseEntity<Unit> {
        val result = addressService.updateAddress(rq.id, rq.address)
        return if (result != null) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAddress(@RequestBody rq: DeleteAddressRq): ResponseEntity<Unit> {
        val result = addressService.deleteAddress(rq.id)
        return if (result != null) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}