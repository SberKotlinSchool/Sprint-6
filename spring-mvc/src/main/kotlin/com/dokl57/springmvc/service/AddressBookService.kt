package com.dokl57.springmvc.service

import com.dokl57.springmvc.model.Entry
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class AddressBookService {
    private var database: ConcurrentHashMap<Long, Entry> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(0)

    init {
        database[0] = Entry("InitialName", "InitialAddress")
    }

    fun addEntry(entry: Entry): Long {
        val entryId = id.incrementAndGet()
        database[entryId] = entry
        return entryId
    }

    fun getEntries(query: String?): Set<Map.Entry<Long, Entry>> {
        if (!query.isNullOrEmpty()) {
            return database.entries.apply { filter { it.value.address.startsWith(query, true) } }
        }
        return database.entries
    }

    fun findEntryById(id: Long): Entry? {
        return database[id]
    }

    fun editEntry(id: Long, entry: Entry): Entry? {
        database[id] ?: return null
        database[id] = entry
        return entry
    }

    fun deleteEntry(id: Long): Entry? {
        val result = database[id] ?: return null
        database.remove(id)
        return result
    }
}