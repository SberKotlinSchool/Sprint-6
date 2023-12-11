package ru.sber.springmvc.repository

import java.util.concurrent.CopyOnWriteArrayList
import org.springframework.stereotype.Repository
import ru.sber.springmvc.domain.Person

@Repository
class PersonRepositoryImpl : PersonRepository {
    private val personsStorage = CopyOnWriteArrayList<Person>()

    override fun add(person: Person) {
        personsStorage.add(person)
    }

    override fun getAll(): List<Person> {
        return ArrayList(personsStorage)
    }

    override fun getByName(name: String): List<Person> {
        return personsStorage.filter { it.name == name }
    }

    override fun getById(id: Int): Person? {
        return personsStorage.find { it.id == id }
    }

    override fun update(person: Person) {
        delete(person.id)
        personsStorage.add(person)
    }

    override fun delete(id: Int) {
        personsStorage.removeIf { it.id == id }
    }
}