package org.example.mvc.services

import org.example.mvc.dto.AddressBookEntry

interface AddressBookService {

    fun addEntry(entry: AddressBookEntry)

    fun getEntries(
        firstName: String?,
        lastName: String?,
        phone: String?,
        email: String?
    ): List<AddressBookEntry>

    fun getEntryById(id: Long): AddressBookEntry?

    fun updateEntry(entry: AddressBookEntry)

    fun deleteEntryById(id: Long)

}