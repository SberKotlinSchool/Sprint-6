package ru.sber.springmvc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.springmvc.model.Person
import ru.sber.springmvc.repository.PersonRepository

@Service
class PersonServiceImpl @Autowired constructor(private val personRepository: PersonRepository) : PersonService {
    override fun add(person: Person) {
        personRepository.add(person)
    }

    override fun getAllContains(name: String): List<Person> {
        return personRepository.getAllContains(name)
    }

    override fun getAll(): List<Person> {
        return personRepository.getAll()
    }

    override fun getById(id: Int): Person? {
        return personRepository.getById(id)
    }

    override fun update(person: Person) {
        personRepository.update(person)
    }

    override fun delete(id: Int) {
        personRepository.delete(id)
    }
}