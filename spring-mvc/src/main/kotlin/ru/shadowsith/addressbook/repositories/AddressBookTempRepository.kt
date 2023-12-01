package ru.shadowsith.addressbook.repositories

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import ru.shadowsith.addressbook.dto.Record
import java.util.*

@Repository
class AddressBookTempRepository : AddressBookRepository {
    private val records = ConcurrentHashMap<String, Record>()
    override fun create(record: Record): Record {
        val guid = UUID.randomUUID().toString()
        val newRecord = record.copy(guid = guid)
        records[guid] =newRecord
        return newRecord
    }

    override fun update(guid: String, record: Record) {
        records[guid] = record
    }

    override fun readAll(): List<Record> {
        return records.map { it.value }
    }

    override fun read(guid: String): Record? {
        return records[guid]
    }

    override fun findByName(name: String): List<Record> {
        return records.filter { it.value.name == name }.values.toList()
    }

    override fun delete(guid: String){
        records.remove(guid)
    }
}