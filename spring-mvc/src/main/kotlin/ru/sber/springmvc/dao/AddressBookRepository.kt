package ru.sber.springmvc.dao

import org.springframework.beans.factory.InitializingBean
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
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

    @PostFilter("hasPermission(filterObject, 'READ')")
    fun getAll(): List<Record> {
        return records.values.toList()
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun updateRecord(id: Long, record: Record): Record? {
        return records.put(id, record)
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun addRecord(record: Record): Record? {
        return records.put(record.id, record)
    }
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    fun getById(id: Long): Record? {
        return records.getValue(id)
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun deleteById(id: Long): Record? {
      return  records.remove(id)
    }
}