package ru.sber.agadressbook.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.repository.AddressBookRepository

@Service
class AddressBookWebService @Autowired constructor(val addressBookRepository: AddressBookRepository) {

    fun getRecordId(id : Int) : Person? {
        println("We are in service getPersonById")
        return addressBookRepository.getRecordById(id)
    }

    fun addRecord(person: Person) : Person? {
        println("We are in service addPerson")
        println("We are in service $person")
        return addressBookRepository.addRecord(person)
    }
}