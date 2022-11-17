package ru.sber.agadressbook.repository

import org.springframework.stereotype.Repository
import ru.sber.agadressbook.models.Person

@Repository
class AddressBookRepository {

    //var addressBookDataBase : HashMap<Int, Person> = hashMapOf(1 to Person("AAA","BBB","CCC"))
    var addressBookDataBase : HashMap<Int, Person> = hashMapOf()


    init {
        addressBookDataBase[1] = Person("Пиастр","555-55-55","Печерская ул.")
        addressBookDataBase[2] = Person("Инокентий","777-77-77","Приморская ул.")
    }

    fun getRecordById(id: Int) : Person? {
       return addressBookDataBase[id]
    }

    fun addRecord(person: Person) : Person? {
        val maxIndex = addressBookDataBase.keys.maxByOrNull { it }!!
        addressBookDataBase.keys.maxByOrNull { it }
        return if (maxIndex != null) addressBookDataBase.put(maxIndex,person) else addressBookDataBase.put(maxIndex, person)
    }

    fun deleteRecord(id: Int) : Person? {
        return addressBookDataBase.remove(id)
    }

    fun editRecord(id: Int, person: Person): Person? {
        return addressBookDataBase.put(id, person)
    }

}