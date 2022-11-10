package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.Customer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class CustomerRepository {
    private val addressBook = ConcurrentHashMap<Int, Customer>()
    private val customerId: AtomicInteger = AtomicInteger(0)

    fun add(customer: Customer) {
        val id = customerId.incrementAndGet()
        addressBook[id] = customer.copy(id = id)
    }

    fun getAll(fio: String?): List<Customer> {
        return addressBook.values
            .filter { (fio == null) || it.fio == fio }
            .toList()
    }

    fun getById(id: Int): Customer? = addressBook[id]

    fun update(client: Customer) {
        addressBook[client.id!!] = client
    }

    fun delete(id: Int) {
        addressBook.remove(id)
    }
}