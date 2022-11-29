package ru.sbrf.school.kotlin.springmvc.repository

import ru.sbrf.school.kotlin.springmvc.entity.Person

interface AddressBookRepository {
    fun getAll():List<Person>
    fun save(person: Person):Person?
    fun getById(id: Long):Person?
    fun delete(id: Long)
}