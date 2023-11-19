package com.example.mvcexampleproject.repository

import com.example.mvcexampleproject.domain.Address
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Component
class AddressRepository {
    private val countId = AtomicInteger(0)
    private val addresses = ConcurrentHashMap<Int, Address>()

    fun getList() = addresses.values.toList()

    fun get(id: Int) = addresses[id]

    fun add(address: Address) {
        val id = countId.incrementAndGet()
        addresses.putIfAbsent(id, address.apply { this.id = id })
    }

    fun edit(id: Int, address: Address) {
        addresses[id] = address.apply { this.id = id }
    }

    fun delete(id: Int) {
        addresses.remove(id)
    }
}