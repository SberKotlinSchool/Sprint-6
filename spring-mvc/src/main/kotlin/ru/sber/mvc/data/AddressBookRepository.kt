package ru.sber.mvc.data

import org.springframework.stereotype.Repository
import ru.sber.mvc.domain.Record
import java.util.concurrent.ConcurrentHashMap

interface AddressBookRepository {

    fun add(record: Record): String

    fun getAll(): List<Record>

    fun getById(id: String): Record

    fun editById(id: String, record: Record): Record

    fun delete(id: String)
}

@Repository
class AddressBookRepositoryImpl : AddressBookRepository {

    private val addressBookMap = ConcurrentHashMap<String, Record>().apply {
        listOf(
            Record("Vasya", "+79998765432", "Moscow"),
            Record("Dima", "+79876543211", "Saint-Petersburg", "Gym buddy")
        ).forEach { put(it.id, it) }
    }

    override fun add(record: Record): String =
        record.id.also { addressBookMap[it] = record }.toString()

    override fun getAll(): List<Record> =
        addressBookMap.values.toList()

    override fun getById(id: String): Record =
        addressBookMap[id] ?: throw java.lang.IllegalArgumentException("No record")

    override fun editById(id: String, record: Record): Record {
        addressBookMap.replace(id, record.copy(id = id))

        return addressBookMap[id] ?: throw java.lang.IllegalArgumentException("No record")
    }

    override fun delete(id: String) {
        addressBookMap.remove(id)
    }
}
