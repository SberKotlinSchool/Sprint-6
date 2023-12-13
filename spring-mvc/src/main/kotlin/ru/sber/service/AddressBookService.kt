package ru.sber.service

import org.springframework.stereotype.Service
import ru.sber.model.Person
import ru.sber.repository.AddressBookRepository

@Service
class AddressBookService(private val addressBookRepository: AddressBookRepository) {

    fun getAllPersons(): List<Person> = addressBookRepository.findAll()

    fun getPersonById(id: Long) = addressBookRepository.findById(id)

    fun addNewPerson(person: Person) {
        addressBookRepository.insert(person)
    }

    fun updatePersonInfo(id: Long, person: Person) {
        addressBookRepository.updateById(id, person)
    }

    fun deletePerson(id: Long) {
        addressBookRepository.deleteById(id)
    }
}