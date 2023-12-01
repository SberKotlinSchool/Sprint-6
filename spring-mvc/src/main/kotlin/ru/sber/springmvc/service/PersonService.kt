package ru.sber.springmvc.service

import ru.sber.springmvc.model.Person

interface PersonService {
    fun add(person: Person)
    fun getAllContains(name: String): List<Person>
    fun getAll(): List<Person>
    fun getById(id: Int): Person?
    fun update(person: Person)
    fun delete(id: Int)
}