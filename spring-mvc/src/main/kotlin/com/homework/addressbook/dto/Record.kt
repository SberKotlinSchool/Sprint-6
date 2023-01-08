package com.homework.addressbook.dto;

import java.util.concurrent.atomic.AtomicInteger

data class Record(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: String
) {

    constructor() : this("", "","","") {
    }

    var id: Int = GeneratorId.nextId();

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 25 * result + lastName.hashCode()
        result = 25 * result + phoneNumber.hashCode()
        result = 25 * result + address.hashCode()
        result = 25 * result + id.hashCode()
        return result
    }

    override fun equals(other: Any?)
            = (other is Record)
            && firstName  == other.firstName
            && lastName == other.lastName
            && phoneNumber == other.phoneNumber
            && address == other.address
}

object GeneratorId {

    private val count = AtomicInteger(0);

    fun nextId(): Int {
        return count.incrementAndGet()
    }
}