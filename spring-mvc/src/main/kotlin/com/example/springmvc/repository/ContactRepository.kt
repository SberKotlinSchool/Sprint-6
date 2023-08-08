package com.example.springmvc.repository

import com.example.springmvc.model.Contact
import org.springframework.stereotype.Repository
import java.util.concurrent.CopyOnWriteArrayList

@Repository
class ContactRepository {

    private var id = 0
    private val addressBook = CopyOnWriteArrayList<Contact>()

    init {
        addressBook.add(Contact(++id, "Tom", "111-11-11"))
        addressBook.add(Contact(++id, "Kate", "222-22-22"))
    }

    fun list(): List<Contact>{
        return addressBook
    }

    fun view(id: Int): Contact? {
        return addressBook.find { it.id == id }
    }

    fun add(contact: Contact) {
        contact.id = ++id
        addressBook.add(contact)
    }

    fun edit(editedContact: Contact): Boolean {
        val contact = addressBook.find { it.id == editedContact.id }
            ?: return false
        contact.name = editedContact.name
        contact.phone = editedContact.phone
        return true
    }

    fun delete(id: Int): Boolean{
        return addressBook.removeIf { it.id == id }
    }
}