package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.dto.Address
import java.util.concurrent.ConcurrentHashMap

@Repository
class AddressBook {

    var book: ConcurrentHashMap<Int, Address> = ConcurrentHashMap()
    var id: Int  = 0
    fun add( address: Address) = book.put(id++, address)
    fun delete(id: Int) = book.remove(id)
    fun getAll() = book.toList()
    fun getById(id: Int) = book[id]
    fun update(id: Int, address: Address): Address? {
        book.replace(id, address )
        return  getById(id)
    }
}