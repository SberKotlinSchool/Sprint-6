package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.Person
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class AddressBookRepository {
    private val atomicId = AtomicLong(0)
    private val addressBook = ConcurrentHashMap<Long, Person>()

    init {
        insert(
                Person(
                        0,
                        "Anton",
                        "Moscow",
                        "qwe@sbrfff.ru"
                )
        )
    }

    fun insert(person: Person) {
        addressBook[person.id] = person
    }

    fun updateById(id: Long, person: Person) {
        addressBook[id] = person
    }

    fun deleteById(id: Long) {
        addressBook.remove(id)
    }

    fun findById(id: Long): Person? = addressBook[id]

    fun findAll(): List<Person> = addressBook.values.toList()
}