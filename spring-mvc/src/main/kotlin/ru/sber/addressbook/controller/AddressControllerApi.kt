package ru.sber.addressbook.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.sber.addressbook.dto.AddressModel
import ru.sber.addressbook.dto.ResponseDTO
import ru.sber.addressbook.service.AddressService

@RestController
@RequestMapping("/api")
class AddressControllerApi(
    private val addressService: AddressService
) {
  @GetMapping("/list")
  fun getListOfAllAddresses() = ResponseDTO(
      data = addressService.getAddressList().values.toList()
  )
  @GetMapping("{id}")
  fun getAddress(@PathVariable id: Long) = ResponseDTO(
      data = addressService.getAddressById(id)
  )

  @PutMapping("{id}/update")
  fun updateAddress(@PathVariable id: Long, @RequestBody address: AddressModel) = ResponseDTO(
      data = addressService.updateAddress(id, address)
  )

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun removeAddress(@PathVariable id: Long) {
    addressService.deleteAddressById(id)
  }
}