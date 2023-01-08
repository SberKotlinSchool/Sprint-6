package sber.mvc.application.repository

import sber.mvc.application.model.AddressBookEntry
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class AddressBookRepository {

    private val entries = ConcurrentHashMap<Long, AddressBookEntry>()
    private val idGenerator: AtomicLong = AtomicLong(0)

    fun add(t: AddressBookEntry) {
        t.id = idGenerator.incrementAndGet()
        entries[t.id] = t
    }

    fun update(t: AddressBookEntry) {
        entries[t.id] = t
    }

    fun get(id: Long): AddressBookEntry? {
        return entries[id]
    }

    fun getAll(): List<AddressBookEntry> {
        return entries.values.toList()
    }

    fun deleteById(id: Long) {
        entries.remove(id)
    }
}