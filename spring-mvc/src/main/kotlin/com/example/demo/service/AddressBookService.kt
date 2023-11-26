package com.example.demo.service

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookService {

    private val map = ConcurrentHashMap<String, AddressBookModel>()

    fun add(model: AddressBookModel) {
        map[model.id] = model
    }

    fun get(id: String): AddressBookModel? {
        return map[id]
    }

    fun getAll(): List<AddressBookModel> {
        return map.values.sortedBy(AddressBookModel::name)
    }

    fun delete(id: String) {
        map.remove(id)
    }

    fun edit(id: String, model: AddressBookModel): AddressBookModel? {
        map.replace(id, model)
        return map[id]
    }
}

data class AddressBookModel(
    val name: String,
    val phone: String,
    val email: String,
) {
    @JsonIgnore
    val id = "$name-$phone-$email"
}