package com.homework.addressbook.service;

import com.homework.addressbook.dto.Record
import com.homework.addressbook.repository.AddressBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressBookService @Autowired constructor(val addressBookRepository: AddressBookRepository) {


    fun addRecord(record: Record) {
        addressBookRepository.addRecord(record);
    }

    fun getRecords(): List<Record> {
        return addressBookRepository.getRecords();
    }

    fun getCurrentRecord(id: Int): Record? {
        return addressBookRepository.getCurrentRecord(id);
    }

    fun editRecord(id: Int, record: Record) {
        addressBookRepository.editRecord(id, record);
    }

    fun deleteRecord(id: Int) {
        addressBookRepository.deleteRecord(id);
    }

}
