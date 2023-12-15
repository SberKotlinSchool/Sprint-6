package com.example.adresbook.service

import com.example.adresbook.AddressBookRepository
import com.example.adresbook.model.BookRecord
import org.springframework.stereotype.Service

@Service
class AddressBookService(
    private val addressBookRepository: AddressBookRepository
) {
    fun getAllBookRecords() = addressBookRepository.pseudoDataBase

    fun getList(address: String?, phone: String?) = addressBookRepository.pseudoDataBase
        .filter { address.isNullOrEmpty() || it.address == address }
        .filter { phone.isNullOrEmpty() || it.phone == phone }

    fun getBookRecord(id: Long) = addressBookRepository.pseudoDataBase.first { it.id == id }

    fun deleteBookRecord(id: Long) = addressBookRepository.pseudoDataBase.remove(getBookRecord(id))

    fun editBookRecord(bookRecord: BookRecord, id: Int): BookRecord {
        addressBookRepository.pseudoDataBase[id] = bookRecord
        return addressBookRepository.pseudoDataBase[id]
    }

    fun addBookRecord(bookRecord: BookRecord) = addressBookRepository.pseudoDataBase.add(bookRecord)
}