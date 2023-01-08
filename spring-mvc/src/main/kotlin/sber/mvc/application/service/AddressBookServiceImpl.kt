package sber.mvc.application.service

import sber.mvc.application.model.AddressBookEntry
import sber.mvc.application.repository.AddressBookRepository
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
        address: String?,
        phone: String?,
        email: String?
    ): List<AddressBookEntry> {
        return addressBookRepository.getAll()
            .asSequence()
            .filter { firstName.isNullOrBlank() || it.firstName == firstName }
            .filter { lastName.isNullOrBlank() || it.lastName == lastName }
            .filter { address.isNullOrBlank() || it.address == address }
            .filter { phone.isNullOrBlank() || it.phone == phone }
            .filter { email.isNullOrBlank() || it.email == email }
            .toList()
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