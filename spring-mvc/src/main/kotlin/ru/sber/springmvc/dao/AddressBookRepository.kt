package ru.sber.springmvc.dao

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import ru.sber.springmvc.domain.Record
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong


@Repository
class AddressBookRepository {
    private val counter = AtomicLong(0)
    var records: ConcurrentHashMap<Long, Record> = ConcurrentHashMap()

    init {
        records.put(counter.incrementAndGet(), Record(1, "Red Dragon", "Lenina str, 2"))
        records.put(counter.incrementAndGet(), Record(2, "Lenin's Library", "Voskova str, 2"))
    }


    fun getAll(): List<Record> {
        return records.values.toList()
    }

    fun updateRecord(id: Long, record: Record): Record? {
        return records.put(id, record)
    }

    fun addRecord(record: Record): Record? {
        return records.put(record.id, record)
    }

    fun getById(id: Long): Record? {
        return records.getValue(id)
    }

    fun deleteById(id: Long) {
        records.remove(id)
    }

}