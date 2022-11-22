package ru.sber.ufs.cc.kulinich.springmvc.repositories

import org.springframework.stereotype.Repository
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel
import java.util.concurrent.ConcurrentHashMap

@Repository
class AdressBookRepository {
    private val db = ConcurrentHashMap<Int, AddressBookModel>()

    fun add(model : AddressBookModel) : Int {
        // считаю, что у дових не людей не может быть один номер телефона
        model.id =model.phone.hashCode()
        db[model.id] = model
        return model.id
    }

    fun getAll() : List<Pair<Int,AddressBookModel>> {
        return db.toList()
    }

    fun getById(id : Int) : AddressBookModel? {
        return db[id]
    }

    fun edit(id: Int, model: AddressBookModel){
        db[id] = model
    }

    fun delete(id : Int) {
        db.remove(id)
    }


}