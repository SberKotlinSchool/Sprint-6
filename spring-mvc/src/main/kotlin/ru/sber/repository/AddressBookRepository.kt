package ru.sber.repository

import ru.sber.model.Note

interface AddressBookRepository {
    fun getById(id: Long): Note?
    fun getAll(): List<Note>
    fun searchWithFilter(name: String? = null, address: String? = null, phone: String? = null): List<Note>
    fun searchByName(name: String): List<Note>
    fun searchByAddress(address: String): List<Note>
    fun searchByPhone(phone: String): List<Note>
    fun create(note: Note)
    fun deleteById(id: Long)
    fun updateById(id: Long, note: Note)
    fun deleteAll()
}