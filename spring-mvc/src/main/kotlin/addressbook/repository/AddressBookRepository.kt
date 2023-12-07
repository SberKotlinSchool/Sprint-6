package addressbook.repository

import addressbook.model.Person
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class AddressBookRepository {
    private val index: AtomicInteger = AtomicInteger(0)
    private val dataBase = ConcurrentHashMap<Int, Person>()

    init {
        add(
            Person(
                firstName = "Kirill",
                lastName = "Timofeev",
                email = "test@gmai.com",
                phoneNumber = "892313232123"
            )
        )
    }

    fun getAllContacts() = dataBase.values.toList()

    fun getById(id: Int) = dataBase[id]

    fun getByFirstName(firstName: String): Person? {
        return dataBase.values.firstOrNull { it.firstName == firstName }
    }

    final fun add(contact: Person): Int {
        val id = index.incrementAndGet()
        contact.id = id
        dataBase[id] = contact
        return id
    }

    fun edit(id: Int, person: Person) {
        person.id = id
        dataBase[id] = person
    }

    fun delete(id: Int) {
        index.decrementAndGet()
        dataBase.remove(id)
    }
}