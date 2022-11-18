package ru.sber.agadressbook.repository

import org.springframework.stereotype.Repository
import ru.sber.agadressbook.models.Person

@Repository
class AddressBookRepository {

    var addressBookDataBase : HashMap<Int, Person> = hashMapOf()


    init {
        addressBookDataBase[1] = Person(1,"Пиастр","555-55-55","Печерская ул.")
        addressBookDataBase[2] = Person(2,"Инокентий","777-77-77","Приморская ул.")
    }

    fun getRecordById(id: Int) : Person? {
       return addressBookDataBase[id]
    }

    fun addRecord(id: Int, person: Person) : Person? {
        return addressBookDataBase.put(id,person)
    }

    fun deleteRecord(id: Int) : Person? {
        return addressBookDataBase.remove(id)
    }

    fun editRecord(id: Int, person: Person): Person? {
        return addressBookDataBase.put(id, person)
    }

    fun getAllRecords(): HashMap<Int, Person> {
        return addressBookDataBase;
    }

}