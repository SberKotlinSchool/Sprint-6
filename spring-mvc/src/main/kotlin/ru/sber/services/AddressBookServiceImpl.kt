package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.model.Customer
import ru.sber.repository.CustomerRepository

@Service
class AddressBookServiceImpl @Autowired constructor(val repository: CustomerRepository) : AddressBookService {
    override fun add(customer: Customer) {
        repository.add(customer)
    }

    override fun getAll(fio: String?): List<Customer> {
       return repository.getAll(fio)
    }

    override fun getById(id: Int): Customer? {
        return repository.getById(id)
    }

    override fun update(customer: Customer) {
        repository.update(customer)
    }

    override fun delete(id: Int) {
      repository.delete(id)
    }
}