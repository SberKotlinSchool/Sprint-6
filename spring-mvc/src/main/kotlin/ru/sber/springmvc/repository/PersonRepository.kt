package ru.sber.springmvc.repository

import ru.sber.springmvc.domain.Person

interface PersonRepository {
    fun add(person: Person)

    fun getAll(): List<Person>

    fun getByName(name: String): List<Person>

    fun getById(id: Int): Person?

    fun update(person: Person)

    fun delete(id: Int)
}