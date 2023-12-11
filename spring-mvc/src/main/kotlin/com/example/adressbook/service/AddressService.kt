package com.example.adressbook.service

import com.example.adressbook.dto.AddressModel
import com.example.adressbook.exception.BusinessException
import com.example.adressbook.repository.AddressRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

    fun saveAddress(address: AddressModel) = addressRepository.saveAddress(address)
        ?: throw BusinessException(HttpStatus.BAD_REQUEST, "Не удалось сохранить адрес $address")

    fun updateAddress(id: Long, address: AddressModel) = addressRepository.updateAddressById(id, address)
        ?: throw BusinessException(HttpStatus.BAD_REQUEST, "Не удалось обновить адрес $address")

    fun deleteAddressById(id: Long) {
        if (addressRepository.deleteAddressById(id) == null) {
            throw BusinessException(HttpStatus.BAD_REQUEST, "Не удалось удалить адрес с id=$id")
        }
    }

    fun getAddressById(id: Long) = addressRepository.getAddressById(id)
        ?: throw BusinessException(HttpStatus.NOT_FOUND, "Не удалось получить адрес по id = $id")

    fun getAddressList() = addressRepository.getAllAdresses()
}