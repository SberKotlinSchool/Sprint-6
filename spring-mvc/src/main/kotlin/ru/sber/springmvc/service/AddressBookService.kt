package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.exception.AddressBookException
import ru.sber.springmvc.model.AddressBookRow

@Service
class AddressBookService {
    private val book = HashMap<String, String>()

    fun add(row: AddressBookRow) {
        if (book.containsKey(row.name)) {
            throw AddressBookException("Row ${row.name} already exists!")
        }
        book[row.name] = row.address
    }

    fun get(id: String): AddressBookRow {
        if (!book.containsKey(id)) {
            throw AddressBookException("Row $id not exists!")
        }

        return AddressBookRow(id, book.getValue(id))
    }

    fun getAll(): List<AddressBookRow> = book.toList()
        .map { row -> AddressBookRow(row.first, row.second) }

    fun edit(id: String, address: String) {
        if (!book.containsKey(id)) {
            throw AddressBookException("No such row $id!")
        }
        book[id] = address
    }

    fun delete(id: String) {
        if (!book.containsKey(id)) {
            throw AddressBookException("No such row $id!")
        }
        book.remove(id)
    }

}
