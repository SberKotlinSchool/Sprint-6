package addressbook.service

import addressbook.model.Person
import addressbook.repository.AddressBookRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.servlet.http.Cookie


@Service
class AuthService {

    @Value("\${login}")
    private lateinit var userName: String

    @Value("\${pass}")
    private lateinit var password: String
    fun checkCredentials(username: String?, password: String?): Boolean {
        return userName == username && this.password == password
    }

    fun checkCookie(cookie: Array<Cookie>?): Boolean {
        val loginTimeCookie = cookie?.find { it.name == "auth" }
        val isValidLoginTime = loginTimeCookie?.let {
            LocalDateTime.parse(it.value) > LocalDateTime.now()
        }?: false

        return isValidLoginTime
    }
}

@Service
class AddressBookService(private val addressBookRepository: AddressBookRepository) {

    fun getPersonByFirstName(firstName: String): List<Person> {
        return addressBookRepository.getByFirstName(firstName)?.let {
            listOf(it)
        } ?: run {
            listOf()
        }
    }

    fun getAllPersons(): List<Person> {
        return addressBookRepository.getAllContacts()
    }

    fun getPersonById(id: Int): Person? {
        return addressBookRepository.getById(id)
    }

    final fun addPerson(person: Person): Int {
        return addressBookRepository.add(person)
    }

    fun editPerson(id: Int, person: Person) {
        addressBookRepository.edit(id, person)
    }

    fun deletePersonById(id: Int) {
        addressBookRepository.delete(id)
    }
}
