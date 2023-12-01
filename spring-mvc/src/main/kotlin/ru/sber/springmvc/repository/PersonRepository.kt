package ru.sber.springmvc.repository

import ru.sber.springmvc.model.Person

interface PersonRepository {
    fun add(person: Person)
    fun getAll(): List<Person>
    fun getAllContains(name: String): List<Person>
    fun getById(id: Int): Person?
    fun update(person: Person)
    fun delete(id: Int)
}