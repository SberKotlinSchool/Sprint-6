package ru.sber.repository

import org.springframework.stereotype.Component
import ru.sber.dto.Address
import java.util.concurrent.ConcurrentHashMap

@Component
class AddressBookRepository {
    private var id = 0L
    private val db = ConcurrentHashMap<Long, Address>()

    fun add(address: Address) = db.put(id++, address)
    fun get(id: Long) = db[id]
    fun getAll() = db.toMap()
    fun delete(id: Long) = db.remove(id)
    fun update(id: Long, address: Address) = db.replace(id, address)
}