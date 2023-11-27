package com.example.springmvcsber.service

import com.example.springmvcsber.entity.Address
import com.example.springmvcsber.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val repository: AddressRepository) {

    fun findAddressesByCity(city: String?) = repository.getAllAddresses()
        .apply { if (city != null) filter { address -> address.city == city } }

    fun get(id: Int) = repository.get(id)

    fun add(address: Address) = repository.add(address)

    fun edit(id: Int, address: Address) = repository.edit(id, address)

    fun delete(id: Int) = repository.delete(id)
}