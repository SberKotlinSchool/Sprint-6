package com.example.adressbook.controller

import com.example.adressbook.dto.AddressModel
import com.example.adressbook.dto.ResponseDTO
import com.example.adressbook.service.AddressService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AddressApiController(
    private val addressService: AddressService
) {

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addAddress(@RequestBody address: AddressModel) = ResponseDTO(data = addressService.saveAddress(address)).also {
        println("Пришло $address")
    }

    @GetMapping("/list")
    fun viewListOfAddresses() = ResponseDTO(
        data = addressService.getAddressList().values.toList()
    )

    @GetMapping("{id}")
    fun viewAddress(@PathVariable id: Long) = ResponseDTO(
        data = addressService.getAddressById(id)
    )

    @PutMapping("{id}/edit")
    fun editAddress(@PathVariable id: Long, @RequestBody address: AddressModel) = ResponseDTO(
        data = addressService.updateAddress(id, address)
    )

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAddress(@PathVariable id: Long) {
        addressService.deleteAddressById(id)
    }
}