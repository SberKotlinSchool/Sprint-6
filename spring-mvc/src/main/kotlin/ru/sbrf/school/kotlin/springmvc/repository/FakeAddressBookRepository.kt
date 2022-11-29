package ru.sbrf.school.kotlin.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sbrf.school.kotlin.springmvc.entity.Person
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class FakeAddressBookRepository : AddressBookRepository {
    private val counter = AtomicLong(0)

    private val fakeRepository = ConcurrentHashMap(
        mapOf(
            counter.addAndGet(0) to Person(id = 0, name = "Vasiliy", phone = "55-12-09"),
            counter.incrementAndGet() to Person(id = 1, name = "Sveta", phone = "945 098 00 91")
        )
    )


    override fun getAll(): List<Person> {
        return fakeRepository.toList().map { it.second }
    }

    override fun save(person: Person) : Person?{
        var id = person.id
        if (id == null) {
            id = counter.incrementAndGet()
            person.id = id
        }
        fakeRepository[id] = person
        return fakeRepository[id]
    }

    override fun getById(id: Long): Person? {
        val person = fakeRepository[id]
        person?.id = id
        return person
    }

    override fun delete(id: Long) {
        fakeRepository.remove(id)
    }
}