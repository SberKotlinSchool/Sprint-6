package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import ru.sber.springmvc.domain.*

@Service
interface AddressBookService {
    fun insert(record: Record): Record

    fun getAll(): ConcurrentHashMap<Long, Record>

    fun getByName(name: String): List<Record?>?

    fun getById(id: Long?): Optional<Record?>?

    fun deleteById(id: Long?)
}