package org.example.mvc.services

import org.example.mvc.dto.AddressBookEntry
import org.example.mvc.repositoties.AddressBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressBookServiceImpl(@Autowired val addressBookRepository: AddressBookRepository) :
    AddressBookService {

    override fun addEntry(entry: AddressBookEntry) {
        addressBookRepository.add(entry)
    }

    override fun getEntries(
        firstName: String?,
        lastName: String?,
        phone: String?,
        email: String?
    ): List<AddressBookEntry> {
        return addressBookRepository.getAll()
            .filter { firstName.isNullOrBlank() || it.name == firstName }
            .filter { lastName.isNullOrBlank() || it.lastName == lastName }
            .filter { phone.isNullOrBlank() || it.phone == phone }
            .filter { email.isNullOrBlank() || it.phone == email }
    }

    override fun getEntryById(id: Long): AddressBookEntry? {
        return addressBookRepository.get(id)
    }

    override fun updateEntry(entry: AddressBookEntry) {
        addressBookRepository.update(entry)
    }

    override fun deleteEntryById(id: Long) {
        addressBookRepository.deleteById(id)
    }
}