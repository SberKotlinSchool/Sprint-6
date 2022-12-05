package com.sbuniver.homework.dto

import org.springframework.stereotype.Component

@Component
class AddressBook {

    var addresses = data().toMutableSet()

    fun add(address: AddressDto): Boolean = addresses.add(address)

    fun delete(address: AddressDto): Boolean = addresses.remove(address)

    fun delete(id: Int): Boolean = addresses.removeIf { it.id == id }

    fun update(address: AddressDto) {
        delete(get(address.id)!!)
        addresses.add(address)
    }

    fun get(id: Int): AddressDto? = addresses.firstOrNull { it.id == id }

    fun maxId(): Int = addresses.maxOf { it.id }

    private fun data() =
        listOf(
            AddressDto(1, "Andrey", "Moscow", "Street1", 1),
            AddressDto(2, "Alexey", "Sochi", "Street2", 2),
            AddressDto(3, "Alexander", "Novosibirsk", "Street3", 3),
            AddressDto(4, "Igor", "Nizhny Novgorod", "Street4", 4),
            AddressDto(5, "Sergey", "Saint-Petersberg", "Street5", 5),
            AddressDto(6, "John", "NYC", "Street6", 6),
        )

}