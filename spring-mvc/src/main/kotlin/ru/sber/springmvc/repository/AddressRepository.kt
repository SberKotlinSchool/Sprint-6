package ru.sber.springmvc.repository

import org.springframework.stereotype.Component
import ru.sber.springmvc.model.Address
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Component
class AddressRepository {
    private val countId = AtomicInteger(0)
    private val addresses = ConcurrentHashMap<Int, Address>()

    init {
        addresses[countId.get()] = Address(countId.get(), "Eric", "South Park", "79119581251")
        addresses[countId.incrementAndGet()] = Address(countId.get(), "Bender", "New York", "1110001111")
        addresses[countId.incrementAndGet()] = Address(countId.get(), "Homer", "Springfield", "78912312359")
    }

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