package ru.sber.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sber.springmvc.model.Person
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

@Repository
class PersonRepositoryImpl : PersonRepository {
    private val addressBook = ConcurrentLinkedQueue<Person>()
    private val personId: AtomicInteger = AtomicInteger(0)

    override fun add(person: Person) {
        addressBook.add(person.copy(id = personId.incrementAndGet()))
    }

    override fun getAll(): List<Person> {
        return addressBook.toList()
    }

    override fun getAllContains(name: String): List<Person> {
        return addressBook.filter { it.name.contains(name) }
    }

    override fun getById(id: Int): Person? {
        return addressBook.filter { it.id == id }.getOrNull(0)
    }

    override fun update(person: Person) {
        delete(person.id)
        addressBook.add(person)
    }

    override fun delete(id: Int) {
        addressBook.removeIf { it.id == id }
    }
}