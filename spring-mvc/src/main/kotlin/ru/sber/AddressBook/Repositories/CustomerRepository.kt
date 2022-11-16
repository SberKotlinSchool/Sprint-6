package ru.sber.AddressBook.Repositories

import org.springframework.stereotype.Repository
import ru.sber.AddressBook.Model.CustomerModel
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class CustomerRepository {
    private val addressBook: ConcurrentHashMap<Int, CustomerModel> = ConcurrentHashMap()
    private val id = AtomicInteger(0)

    init {
        for (i in 1..10) {
            addressBook[id.incrementAndGet()] = CustomerModel(
                firstName = "FirstName$i",
                lastName = "LastName$i",
                middleName = "MiddleName$i",
                phone = "+79982453445",
                address = "Town$i, Street - $i, $i fl.",
                email = "email$i@mail.ru"
            )
        }
    }

    fun create(customer: CustomerModel) {
        val customerId = id.incrementAndGet()
        addressBook.put(customerId, customer)
    }

    fun getAll(): Map<Int,CustomerModel> = addressBook

    fun getCustomersWithParam(
        lastName: String?
    ): Map<Int,CustomerModel> {
        var result: Map<Int, CustomerModel> = addressBook
        if (lastName != null) result = addressBook.filter { it.value.lastName?.contains(lastName) ?: false  }

        return result
    }

    fun getById(id: Int): CustomerModel? {
        return addressBook[id]
    }

    fun update(customerId: Int, updatedCustomerModel: CustomerModel) {
        addressBook[customerId] = updatedCustomerModel
    }

    fun delete(customerId: Int) {
        addressBook.remove(customerId)
    }
}