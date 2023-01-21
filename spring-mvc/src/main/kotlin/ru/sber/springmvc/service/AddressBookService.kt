package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import ru.sber.springmvc.domain.*

@Service
interface AddressBookService {
    fun insert(record: Record): Record?

    fun getAll(): List<Record>

    fun getById(id: Long): Record?

    fun deleteById(id: Long)
}