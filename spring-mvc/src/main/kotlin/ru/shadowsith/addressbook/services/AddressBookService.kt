package ru.shadowsith.addressbook.services

import org.springframework.stereotype.Service
import ru.shadowsith.addressbook.dto.Record
import ru.shadowsith.addressbook.repositories.AddressBookRepository

@Service
class AddressBookService(
    private val addressBookRepository: AddressBookRepository
) {
    fun add(record: Record): Record = addressBookRepository.create(record)

    fun change(guid: String, record: Record): Record? {
        return addressBookRepository.read(guid)?.let {
            val changeRecord = record.copy(
                guid = it.guid,
                createDataTime = it.createDataTime
            )
            addressBookRepository.update(guid, changeRecord)
            changeRecord
        }
    }
    fun delete(guid: String): Record? {
        return addressBookRepository.read(guid)?.let {
            addressBookRepository.delete(it.guid!!)
            it
        }
    }
    fun findAll() = addressBookRepository.readAll()
    fun findByGuid(guid: String) = addressBookRepository.read(guid)
    fun findByName(name: String) = addressBookRepository.findByName(name)
}