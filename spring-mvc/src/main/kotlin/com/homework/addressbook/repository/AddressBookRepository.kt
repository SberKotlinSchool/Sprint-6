package com.homework.addressbook.repository

import com.homework.addressbook.dto.GeneratorId
import java.util.concurrent.ConcurrentHashMap
import com.homework.addressbook.dto.Record;
import com.homework.addressbook.exception.AddressBookException
import org.springframework.stereotype.Repository

@Repository
class AddressBookRepository {

    private val addressDB = ConcurrentHashMap<Int, Record>();

    init {
        addressDB[1] = Record("Падме", "Амидала", "+999-789-789-789", "планета Набу")
        addressDB[2] = Record("Лея", "Органа", "+456-124-358-215", "планета Алдераан")
        addressDB[3] = Record("Хан", "Соло", "+136-4578-2154-1985", "планета Кореллия")
    }

    fun addRecord(newRecord: Record) {
        if (addressDB.containsKey(newRecord.id)) {
            val oldRecord = addressDB[newRecord.id];
            if (oldRecord != null && oldRecord.equals(newRecord)) {
                throw AddressBookException("Запись уже существует")
            } else {
                newRecord.id = GeneratorId.nextId();
                addRecord(newRecord);
            }
        }
        addressDB[newRecord.id] = newRecord;
    }

    fun getRecords(): List<Record> {
        return addressDB.values.toList();
    }

    fun getCurrentRecord(id: Int): Record? {
        return addressDB[id]
    }

    fun editRecord(id: Int, record: Record)
    {
        if (addressDB.containsKey(id)) {
            if(addressDB.values.any { it.equals(record) })
                throw AddressBookException("Запись уже существует")
            addressDB[id] = record;
        }
    }

    fun deleteRecord(id: Int) {
        if (addressDB.containsKey(id)) {
            addressDB.remove(id);
        } else throw AddressBookException("Не найдена запись с id $id")
    }
}