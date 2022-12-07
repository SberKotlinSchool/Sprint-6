package ru.sber.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sber.springmvc.dto.Entry
import java.util.concurrent.ConcurrentHashMap

@Repository
class AppRepository {

    private val entries = ConcurrentHashMap<String, Entry>()

    fun add(entry: Entry) {
        entries[entry.name] = entry
    }

    fun list() = entries.values
        .toList()

    fun view(id: String) = entries[id]

    fun edit(id: String, entry: Entry) {
        entries[id] = entry
    }

    fun delete(id: String) = entries.remove(id)
}