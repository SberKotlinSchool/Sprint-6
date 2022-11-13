package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.Person
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

interface AddressBookRepository {
    fun add(person: Person)
    fun getPersons(fio: String?, address: String?, phone: String?, email: String?): List<Person>
    fun getPersonById(id: Int): Person?
    fun updatePerson(person: Person)
    fun deletePerson(id: Int)
}

@Repository
class AddressBookRepositoryImpl : AddressBookRepository {
    private val addressBook = ConcurrentHashMap<Int, Person>()
    private val idGen: AtomicInteger = AtomicInteger(0)

    override fun add(person: Person) {
        val id = idGen.incrementAndGet()
        addressBook[id] = person.copy(id = id)
    }


    override fun getPersons(fio: String?, address: String?, phone: String?, email: String?): List<Person> {
        var result: List<Person> = addressBook.values.toList()
        if (fio != null) result = result.filter { it.fio == fio }
        if (address != null) result = result.filter { it.address == address }
        if (phone != null) result = result.filter { it.phone == phone }
        if (email != null) result = result.filter { it.email == email }
        return result
    }

    override fun getPersonById(id: Int): Person? = addressBook[id]

    override fun updatePerson(person: Person) {
        addressBook[person.id!!] = person
    }

    override fun deletePerson(id: Int) {
        addressBook.remove(id)
    }
}