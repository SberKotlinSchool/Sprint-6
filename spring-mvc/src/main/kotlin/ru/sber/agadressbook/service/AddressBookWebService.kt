package ru.sber.agadressbook.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.agadressbook.models.Credentials
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.repository.AddressBookRepository


@Service
class AddressBookWebService(@Autowired val addressBookRepository: AddressBookRepository) {

    fun checkUser(credentials : Credentials) : Boolean {
        return credentials.password == "123"
    }

    fun getRecordById(id : Int) : Person? {
        return addressBookRepository.getRecordById(id)
    }

    fun updateRecord(id: Int, person: Person): Person? {
        return addressBookRepository.updateRecord(id, person)
    }

    fun deleteRecord(id: Int): Person? {
        return addressBookRepository.deleteRecord(id)
    }

    fun getAllRecords(): HashMap<Int, Person> {
        return addressBookRepository.getAllRecords()
    }

    fun addRecord(person: Person): Person? {
        return addressBookRepository.addRecord(person)
    }

}