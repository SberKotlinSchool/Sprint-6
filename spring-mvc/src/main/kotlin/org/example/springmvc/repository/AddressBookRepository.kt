package org.example.springmvc.repository

import org.example.springmvc.entity.Contact
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class AddressBookRepository {
    private val countId = AtomicInteger(0)
    private val contacts = ConcurrentHashMap<Int, Contact>()

    fun getAllContacts() = contacts.values.toList()

    fun get(id: Int) = contacts[id]

    fun add(contact: Contact) {
        val id = countId.incrementAndGet()
        contact.id = id
        contacts.putIfAbsent(id, contact)
    }

    fun edit(id: Int, contact: Contact) {
        contact.id = id
        contacts[id] = contact
    }

    fun delete(id: Int) {
        contacts.remove(id)
    }
}