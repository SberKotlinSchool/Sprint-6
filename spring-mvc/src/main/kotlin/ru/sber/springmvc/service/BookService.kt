package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.concurrent.ConcurrentHashMap

data class BookRow(val name: String, val address: String)
@Service
class BookService {
    private val book = ConcurrentHashMap<String, String>()

    fun add(row: BookRow) {
        if (book.containsKey(row.name))
            throw RuntimeException("Row ${row.name} already exists!")
        book[row.name] = row.address
    }

    fun get(id: String): BookRow {
        if (!book.containsKey(id))
            throw RuntimeException("Row $id not exists!")
        return BookRow(id, book.getValue(id))
    }

    fun getAll() = book.toList().map { row -> BookRow(row.first, row.second) }

    fun edit(id: String, address: String) {
        if (!book.containsKey(id))
            throw RuntimeException("No such row $id!")
        book[id] = address
    }

    fun delete(id: String) {
        if (!book.containsKey(id))
            throw RuntimeException("No such row $id!")
        book.remove(id)
    }

    fun deleteAll() = book.clear()
}