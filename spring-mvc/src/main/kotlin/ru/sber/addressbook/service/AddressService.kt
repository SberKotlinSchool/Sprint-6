package ru.sber.addressbook.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.sber.addressbook.dto.AddressModel
import ru.sber.addressbook.exception.AddressException
import ru.sber.addressbook.repository.AddressRepository

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

  fun saveAddress(address: AddressModel) = addressRepository.saveAddress(address)
      ?: throw AddressException(HttpStatus.BAD_REQUEST, "Не удалось сохранить адрес $address")

  fun updateAddress(id: Long, address: AddressModel) = addressRepository.updateAddressById(id, address)
      ?: throw AddressException(HttpStatus.BAD_REQUEST, "Не удалось обновить адрес $address")

  fun deleteAddressById(id: Long) {
    if (addressRepository.deleteAddressById(id) == null) {
      throw AddressException(HttpStatus.BAD_REQUEST, "Не удалось удалить адрес с id=$id")
    }
  }

  fun getAddressById(id: Long) = addressRepository.getAddressById(id)
      ?: throw AddressException(HttpStatus.NOT_FOUND, "Не удалось получить адрес по id = $id")

  fun getAddressList() = addressRepository.getAllAdresses()
}