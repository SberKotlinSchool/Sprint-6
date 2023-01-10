package ru.sber.springmvc.model

import kotlin.random.Random

data class Address(
        val id: Long,
        var addr: String?,
        var user: User
) {
    constructor(): this("", User())
    constructor(address: String, user: User) : this(Random.nextLong(), address, user)

    override fun toString(): String {
        return "id = $id, address = $addr, user = ${user.name}"
    }
}
