package ru.sber.addressbook.repository

import org.springframework.stereotype.Repository
import ru.sber.addressbook.dto.AddressModel
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class AddressRepository(
) {
  private val idGenerator: AtomicLong = AtomicLong(0)
  private val addresses: MutableMap<Long, AddressModel> = ConcurrentHashMap()

  final fun saveAddress(address: AddressModel) = idGenerator.incrementAndGet()
      .let {
        addresses[it] = address.apply { id = it }
        addresses[it]
      }

  fun updateAddressById(id: Long, address: AddressModel): AddressModel? {
    addresses[id] = address.apply { this.id = id }
    return addresses[id]
  }

  fun deleteAddressById(id: Long) = addresses.remove(id)

  fun getAddressById(id: Long) = addresses[id]

  fun getAllAdresses() = addresses
}