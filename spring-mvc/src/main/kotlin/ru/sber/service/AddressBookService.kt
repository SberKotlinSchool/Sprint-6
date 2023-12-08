package ru.sber.service

import org.springframework.stereotype.Service
import ru.sber.model.BaseEntity
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class AddressBookService {
    private var database: ConcurrentHashMap<Long, BaseEntity> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(0)

    init {
        database[0] = BaseEntity("FirstName", "Moscow, Lenin str, 1")
    }

    fun addBaseEntity(baseEntity: BaseEntity): Long {
        val baseEntityId = id.incrementAndGet()
        database[baseEntityId] = baseEntity
        return baseEntityId
    }

    fun getBaseEntities(query: String?): Set<Map.Entry<Long, BaseEntity>> {
        if (!query.isNullOrEmpty()) {
            return database.entries.apply { filter { it.value.address.startsWith(query, true) } }
        }
        return database.entries
    }

    fun findBaseEntityById(id: Long): BaseEntity? {
        return database[id]
    }

    fun editBaseEntity(id: Long, baseEntity: BaseEntity): BaseEntity? {
        database[id] ?: return null
        database[id] = baseEntity
        return baseEntity
    }

    fun deleteBaseEntity(id: Long): BaseEntity? {
        val result = database[id] ?: return null
        database.remove(id)
        return result
    }
}