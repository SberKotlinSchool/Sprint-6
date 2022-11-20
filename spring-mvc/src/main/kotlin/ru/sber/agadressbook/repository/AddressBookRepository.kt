package ru.sber.agadressbook.repository

import org.springframework.stereotype.Repository
import ru.sber.agadressbook.models.Person
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

@Repository
class AddressBookRepository {

    var addressBookDataBase: ConcurrentHashMap<Int, Person> = ConcurrentHashMap()


    init {
        addressBookDataBase[1] = Person(1, "Пиастр", "555-55-55", "Печерская ул.")
        addressBookDataBase[2] = Person(2, "Инокентий", "777-77-77", "Приморская ул.")
    }

    fun getRecordById(id: Int): Person? {
        addressBookDataBase[id]
        return addressBookDataBase[id]
    }

    fun updateRecord(id: Int, person: Person): Person? {
        return addressBookDataBase.put(id, person)
    }

    fun deleteRecord(id: Int): Person? {
        return addressBookDataBase.remove(id)
    }

    fun getAllRecords(): ConcurrentHashMap<Int, Person> {
        return addressBookDataBase;
    }

    fun addRecord(person: Person): Person? {
        if (addressBookDataBase.keys.maxByOrNull { it } == null) person.id = 1 else person.id = addressBookDataBase.keys.maxByOrNull { it }!! + 1
        return addressBookDataBase.put(person.id, person)
    }

}