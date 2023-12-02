package org.example.springmvc.service

import org.example.springmvc.entity.Contact
import org.example.springmvc.repository.AddressBookRepository
import org.springframework.stereotype.Service

@Service
class AddressBookService(private val repository: AddressBookRepository) {

    fun findContactsByCity(city: String?) = repository.getAllContacts()
        .apply { if (city != null) filter { address -> address.city == city } }

    fun get(id: Int) = repository.get(id)

    fun add(contact: Contact) = repository.add(contact)

    fun edit(id: Int, contact: Contact) = repository.edit(id, contact)

    fun delete(id: Int) = repository.delete(id)
}