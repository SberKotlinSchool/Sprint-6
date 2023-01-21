package ru.sber.springmvc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookServiceImpl @Autowired constructor(val repository: AddressBookRepository) : AddressBookService {
    override fun insert(record: Record): Record? {
        return repository.addRecord(record)
    }

    override fun getAll(): List<Record> {
        return repository.getAll()
    }


    override fun getById(id: Long): Record? {
        return repository.getById(id)
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}