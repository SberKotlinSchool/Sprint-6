package ru.sber.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.repository.AddressBookRepository
import ru.sber.model.AddressBookRecord
import ru.sber.model.Contact

@Service
class AddressBookService @Autowired constructor(
    private val addressBookRepository: AddressBookRepository
) {

    fun get(id: Long): AddressBookRecord {
        return addressBookRepository.get(id)
    }

    fun get(query: Map<String, String>?): List<AddressBookRecord> {
        if (query.isNullOrEmpty()) {
            return addressBookRepository.getAll()
        }

        return addressBookRepository.get(Contact(query[Contact.ID], query[Contact.NAME], query[Contact.ADDRESS]))
    }

    fun create(addressBookRecord: AddressBookRecord) {
        addressBookRepository.create(addressBookRecord)
    }

    fun update(id: Long, addressBookRecord: AddressBookRecord) {
        addressBookRepository.update(id, addressBookRecord)
    }

    fun delete(id: Long) {
        addressBookRepository.delete(id)
    }

}
