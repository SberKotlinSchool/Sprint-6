package ru.sber.springmvc.dao

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import ru.sber.springmvc.domain.Record
import java.util.concurrent.ConcurrentHashMap


@Repository
class AddressBookRepository : InitializingBean {

    var records = ConcurrentHashMap<Long, Record>()

    fun getAll(): List<Record> {
        return records.values.toList()
    }

    fun addRecord(id: Long, record: Record) {
        records.put(id, record)
    }

    fun getById(id: Long): Record? {
        return records.get(id)
    }

    override fun afterPropertiesSet() {
        records.put(1, Record(1, "Red Dragon", "Lenina str, 2"))
        records.put(2, Record(2, "Lenin's Library", "Voskova str, 2"))
    }
}