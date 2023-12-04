package ru.sbrf.zhukov.springmvc.service

import org.springframework.stereotype.Service
import ru.sbrf.zhukov.springmvc.data.AddressEntry
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookService {

    private val addressBook = ConcurrentHashMap<Long, AddressEntry>()

    fun addEntry(entry: AddressEntry): AddressEntry {
        val id = generateId()
        val newEntry = AddressEntry(id, entry.name, entry.address)
        addressBook[id] = newEntry
        return newEntry
    }

    fun editEntry(id: Long, entry: AddressEntry): AddressEntry? {
        val existingEntry = addressBook[id]
        if (existingEntry != null) {
            existingEntry.name = entry.name
            existingEntry.address = entry.address
            return existingEntry
        }
        return null
    }

    fun getEntry(id: Long): AddressEntry? {
        return addressBook[id]
    }

    fun getAllEntries(): List<AddressEntry> {
        return ArrayList(addressBook.values)
    }

    fun searchEntries(query: String): List<AddressEntry> {
        return addressBook.values.filter {
            it.name.contains(query, ignoreCase = true) || it.address.contains(
                query,
                ignoreCase = true
            )
        }
    }

    fun deleteEntry(id: Long): Boolean {
        return addressBook.remove(id) != null
    }

    private fun generateId(): Long {
        return System.currentTimeMillis()
    }
}