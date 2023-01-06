package ru.sber.app

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@Service
class AddressBook {
    private var addressBook = ConcurrentHashMap<Int,Any>()

    init {
        addressBook[0] = FormModel("Иван", "Иванов", "Иваново")
        addressBook[1] = FormModel("Дмитрий", "Дмитриев", "Дмитров")
    }

    @Bean
    fun returnObjAddressBook(): AddressBook {
        return AddressBook()
    }

    fun addValue(obj: FormModel){
        var key = Random.nextInt(1000000)
        while (addressBook.keys.any { it == key }) key = Random.nextInt(1000000)
        addressBook[key] = obj
    }

    fun addValue(id: Int, obj: FormModel){
        addressBook[id] = obj
    }

    fun getFields(): Map<Int, FormModel> {
        return addressBook.filter { addressBook[it.key] != "" } as Map<Int, FormModel>
    }

    fun getValue(key: Int): FormModel {
        requireNotNull(addressBook[key]){ return FormModel("С данным ID"," запись в справочнике ","отсутствует")}
        return (addressBook[key] as FormModel?)!!
    }

    fun deleteValue(key: Int){
        addressBook.remove(key)
        addressBook[key] = ""
    }
}