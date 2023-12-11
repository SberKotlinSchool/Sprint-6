package ru.sber.springmvc.service

import ru.sber.springmvc.domain.Person

interface PersonService {
    fun getAll(): List<Person>

    fun getByName(name: String): List<Person>

    fun getById(id: Int): Person?

    fun addPerson(person: Person)

    fun updatePerson(person: Person)

    fun deletePerson(id: Int)
}