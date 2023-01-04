package ru.sber.addressbook.service

import org.springframework.stereotype.Service
import ru.sber.addressbook.data.Contact
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookService {

    private val addressBook = ConcurrentHashMap<Long, Contact>()

    init {
        addressBook[1] = Contact(
            "Попов", "Иван", "Сергеевич",
            LocalDate.parse("01.01.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+7 999 999-99-99", "popovi@mail.ru"
        )
        addressBook[2] = Contact(
            "Попов", "Петр", "Федорович",
            LocalDate.parse("01.01.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+7 123 456-78-99", "popovp@mail.ru"
        )
    }

    fun add(
        firstName: String,
        lastName: String,
        middleName: String?,
        birthDate: LocalDate,
        phoneNumber: String,
        email: String
    ): Contact = add(Contact(firstName, lastName, middleName, birthDate, phoneNumber, email))

    fun add(contact: Contact): Contact = contact.also { addressBook[(addressBook.keys.maxOrNull() ?: 0) + 1] = it }

    fun update(
        id: Long,
        firstName: String,
        lastName: String,
        middleName: String?,
        birthDate: LocalDate,
        phoneNumber: String,
        email: String
    ): Contact = update(id, Contact(firstName, lastName, middleName, birthDate, phoneNumber, email))

    fun update(id: Long, contact: Contact): Contact = contact.also { addressBook[id] = it }

    fun delete(id: Long) {
        addressBook.remove(id)
    }

    fun find(
        firstName: String?,
        lastName: String,
        middleName: String?
    ): Map<Long, Contact> = addressBook.filterValues {
        (firstName.isNullOrEmpty() || it.firstName.equals(firstName))
                && it.lastName.equals(lastName) && (middleName.isNullOrEmpty() || it.middleName.equals(middleName))
    }
        .toMap();

    fun getById(id: Long): Contact? = addressBook[id]

    fun getAll(): Map<Long, Contact> = addressBook
}