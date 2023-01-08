package sber.mvc.application.service

import sber.mvc.application.model.AddressBookEntry

interface AddressBookService {

    fun addEntry(entry: AddressBookEntry)

    fun getEntries(
        firstName: String?,
        lastName: String?,
        address: String?,
        phone: String?,
        email: String?
    ): List<AddressBookEntry>

    fun getEntryById(id: Long): AddressBookEntry?

    fun updateEntry(entry: AddressBookEntry)

    fun deleteEntryById(id: Long)

}