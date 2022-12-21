package ru.sber.springmvc.dao

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import ru.sber.springmvc.domain.Record
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong


@Repository
class AddressBookRepository : InitializingBean {
    private val counter = AtomicLong(0)

    var records = ConcurrentHashMap<Long, Record>()

    fun getAll(): List<Record> {
        return records.values.toList()
    }

    fun addRecord(record: Record) {
        records.put(counter.incrementAndGet(), record)
    }

    fun getById(id: Long): Record? {
        return records.get(id)
    }

    fun deleteById(id: Long) {
        records.remove(id)
    }

    override fun afterPropertiesSet() {
        records.put(counter.addAndGet(0), Record(1, "Red Dragon", "Lenina str, 2"))
        records.put(counter.incrementAndGet(), Record(2, "Lenin's Library", "Voskova str, 2"))
    }
}