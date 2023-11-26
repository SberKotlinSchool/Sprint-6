package com.example.springmvcsber.repository

import com.example.springmvcsber.entity.Address
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger


@Repository
class AddressRepository {
    private val countId = AtomicInteger(0)
    private val addresses = ConcurrentHashMap<Int, Address>()

    fun getAllAddresses() = addresses.values.toList()

    fun get(id: Int) = addresses[id]

    fun add(address: Address) {
        val id = countId.incrementAndGet()
        address.id = id
        addresses.putIfAbsent(id, address)
    }

    fun edit(id: Int, address: Address) {
        address.id = id
        addresses[id] = address
    }

    fun delete(id: Int) {
        addresses.remove(id)
    }
}