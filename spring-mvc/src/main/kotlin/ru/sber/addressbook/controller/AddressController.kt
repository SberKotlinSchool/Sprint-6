package ru.sber.addressbook.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.addressbook.dto.AddressModel
import ru.sber.addressbook.service.AddressService

@Controller
@RequestMapping("/app")
class AddressController(
    private val addressService: AddressService
) {

  @GetMapping("/list")
  fun viewListOfAddresses(model: Model): String {
    val addresses = addressService.getAddressList()
    model.addAttribute("addresses", addresses)
    return "addresses"
  }

  @PostMapping("/add")
  fun addAdress(address: AddressModel): String {
    addressService.saveAddress(address)
    return "redirect:/app/list"
  }

  @GetMapping("/add")
  fun addAdress(model: Model): String {
    model.addAttribute("address", AddressModel())
    return "create-address"
  }

  @GetMapping("{id}")
  fun viewAddress(@PathVariable id: Long, model: Model): String {
    model.addAttribute("address", addressService.getAddressById(id))
    return "view-address"
  }

  @GetMapping("{id}/edit")
  fun editAddress(@PathVariable id: Long, model: Model): String {
    val address = addressService.getAddressById(id)
    model.addAttribute("address", address)
    model.addAttribute("id", id)
    return "update-address"
  }

  @PutMapping("{id}/edit")
  fun editAddress(@PathVariable id: Long, @ModelAttribute address: AddressModel): String {
    addressService.updateAddress(id, address)
    return "redirect:/app/list"
  }

  @DeleteMapping("{id}/delete")
  fun deleteAddress(@PathVariable id: Long): String {
    addressService.deleteAddressById(id)
    return "redirect:/app/list"
  }
}