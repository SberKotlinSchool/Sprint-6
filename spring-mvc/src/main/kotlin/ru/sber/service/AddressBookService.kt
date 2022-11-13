package ru.sber.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.model.Person
import ru.sber.repository.AddressBookRepository

interface AddressBookService {
    fun add(person: Person)
    fun getPersons(fio: String?, address: String?, phone: String?, email: String?): List<Person>
    fun getPersonById(id: Int): Person?
    fun updatePerson(person: Person)
    fun deletePerson(id: Int)
}


@Service
class AddressBookServiceImpl @Autowired constructor(val repository: AddressBookRepository) : AddressBookService {
    override fun add(person: Person) {
        repository.add(person)
    }

    override fun getPersons(fio: String?, address: String?, phone: String?, email: String?): List<Person> =
        repository.getPersons(fio, address, phone, email)

    override fun getPersonById(id: Int): Person? = repository.getPersonById(id)

    override fun updatePerson(person: Person) {
        repository.updatePerson(person)
    }

    override fun deletePerson(id: Int) {
        repository.deletePerson(id)
    }
}