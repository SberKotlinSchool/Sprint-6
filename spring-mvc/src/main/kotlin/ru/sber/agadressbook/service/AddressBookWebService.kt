package ru.sber.agadressbook.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.repository.AddressBookRepository

@Service
class AddressBookWebService @Autowired constructor(val addressBookRepository: AddressBookRepository) {

    fun getPersonById(id : Int) : Person? {
        println("We are in service")
        return addressBookRepository.getPersonById(id)
    }
}