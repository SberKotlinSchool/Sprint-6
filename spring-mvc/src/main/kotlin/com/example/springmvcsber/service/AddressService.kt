package com.example.springmvcsber.service

import com.example.springmvcsber.entity.Address
import com.example.springmvcsber.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val repository: AddressRepository) {

    fun findAddressesByCity(city: String?) = repository.findAll()
        .apply { if (city != null) filter { address -> address.city == city } }

    fun get(id: Long) = repository.findById(id).orElse(null)

    fun add(address: Address) = repository.save(address)

    fun edit(id: Long, address: Address) = get(id)
        ?.apply {
            name = address.name
            city = address.city
            phone = address.phone
        }
        ?.let { repository.save(it) }

    fun delete(id: Long) = get(id)
        ?.let { repository.delete(it) }
}