package ru.sber.addressbook.service

import org.springframework.stereotype.Service
import ru.sber.addressbook.model.Person
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@Service
class PersonService {
    val list = ConcurrentHashMap<Long, Person>(
        mapOf(
            0L to Person(0L, "MyName", "DC"),
            1L to Person(1L, "AnotherName", "MC")
        )
    )

    fun getAll() = list.toList().map { it.second }

    fun getById(id: Long): Person? = list[id]

    fun add(person: Person): Long {
        val id = Random.nextLong()
        person.id = id
        list[id] = person
        return id
    }

    fun delete(id: Long) {
        list.remove(id)
    }

    fun update(person: Person): Person {
        list[person.id!!] = person
        return person
    }
}