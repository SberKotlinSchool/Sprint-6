package ru.sber.repository

import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class DB {

    private val db: ConcurrentHashMap<Long, String> = ConcurrentHashMap()

    fun getById(id: Long) = db[id]
    fun getAll() = db.toMap()
    fun save(address: String) = db.put(db.size.toLong(), address)
    fun saveById(id: Long, address: String) = db.put(id, address)
    @Nullable
    fun delete(id: Long):String? = db.remove(id)
}