package ru.sber.services

import ru.sber.model.Customer

interface AddressBookService {
    fun add(customer: Customer)
    fun getAll(fio: String?): List<Customer>
    fun getById(id: Int): Customer?
    fun update(customer: Customer)
    fun delete(id: Int)
}