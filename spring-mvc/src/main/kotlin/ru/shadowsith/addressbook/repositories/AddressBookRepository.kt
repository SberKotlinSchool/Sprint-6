package ru.shadowsith.addressbook.repositories

import ru.shadowsith.addressbook.dto.Record
interface AddressBookRepository {
    fun create(record: Record): Record
    fun update(guid: String, record: Record)
    fun readAll(): List<Record>
    fun read(guid: String): Record?
    fun findByName(name: String): List<Record>
    fun delete(guid: String)
}