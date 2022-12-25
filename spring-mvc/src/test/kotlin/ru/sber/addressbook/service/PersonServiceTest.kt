package ru.sber.addressbook.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.sber.addressbook.model.Person
import kotlin.random.Random

internal class PersonServiceTest {

    private lateinit var personService: PersonService

    @BeforeEach
    fun setUp() {
        personService = PersonService()
    }

    @Test
    fun getAll() {
        assertEquals(personService.list.size, personService.getAll().size)
    }

    @Test
    fun getById() {
        val id = Random.nextLong()

        assertNull(personService.getById(id))

        val person = Person(id, "SF", "testCity")
        personService.list.put(id, person)

        assertEquals(person, personService.getById(id))
    }

    @Test
    fun add() {
        val person = Person(null, "SF", "testCity")
        val expectedListSize = personService.list.size + 1

        personService.add(person)

        assertEquals(expectedListSize, personService.list.size)
    }

    @Test
    fun delete() {
        val expectedListSize = personService.list.size - 1
        val keyToDelete = personService.list.keys().nextElement()

        personService.delete(keyToDelete)

        assertEquals(expectedListSize, personService.list.size)
    }

    @Test
    fun update() {
        val personToUpdate = personService.list.elements().nextElement()
        val expectedCity = "new city"
        personToUpdate.city = expectedCity

        val actualPerson = personService.update(personToUpdate)

        assertEquals(expectedCity, actualPerson.city)
    }
}