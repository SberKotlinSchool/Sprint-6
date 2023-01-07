package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.model.Address
import ru.sber.springmvc.repository.AddressRepository

@Service
class AddressService(private val repository: AddressRepository) {

    fun getList(query: String?) = repository.getList().apply {
        query?.let { city -> filter { address -> address.city == city } }
    }

    fun get(id: Int) = repository.get(id)

    fun add(address: Address) = repository.add(address)

    fun edit(id: Int, address: Address) = repository.edit(id, address)

    fun delete(id: Int) = repository.delete(id)
}