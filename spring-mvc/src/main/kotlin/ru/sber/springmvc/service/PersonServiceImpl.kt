package ru.sber.springmvc.service

import java.util.concurrent.atomic.AtomicInteger
import org.springframework.stereotype.Service
import ru.sber.springmvc.domain.Person
import ru.sber.springmvc.repository.PersonRepository

@Service
class PersonServiceImpl(private val personRepository: PersonRepository) : PersonService {
    private val idCounter = AtomicInteger(0)

    override fun getAll(): List<Person> {
        return personRepository.getAll()
    }

    override fun getByName(name: String): List<Person> {
        return personRepository.getByName(name)
    }

    override fun getById(id: Int): Person? {
        return personRepository.getById(id)
    }

    override fun addPerson(person: Person) {
        person.id = idCounter.incrementAndGet()
        return personRepository.add(person)
    }

    override fun updatePerson(person: Person) {
        return personRepository.update(person)
    }

    override fun deletePerson(id: Int) {
        personRepository.delete(id)
    }
}