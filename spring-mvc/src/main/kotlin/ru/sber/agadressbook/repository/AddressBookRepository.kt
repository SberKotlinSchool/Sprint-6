package ru.sber.agadressbook.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.sber.agadressbook.models.Person
import java.util.concurrent.ConcurrentHashMap

@Repository
class AddressBookRepository {

    //var addressBookDataBase : HashMap<Int, Person> = hashMapOf(1 to Person("AAA","BBB","CCC"))
    var addressBookDataBase : HashMap<Int, Person> = hashMapOf()


    init {

        addressBookDataBase[1] = Person("AAA","BBB","CCC")
        addressBookDataBase[2] = Person("DDD","EEE","222")
    }

    fun getPersonById(id: Int) : Person? {
       return addressBookDataBase[id]
    }
}