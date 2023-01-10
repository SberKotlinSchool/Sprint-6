package ru.sber.springmvc.model

import kotlin.random.Random

data class User(
        var id : Long,
        var name: String?,
        var login: String?,
        var password: String?,
        var address: Address? = null,
        var state: String = "new"
) {
    constructor(name: String, login: String, password: String) : this(Random.nextLong(), name, login, password, null)
    constructor() : this(Random.nextLong(), null, null, null, null)

    override fun toString(): String {
        return "id = $id, name = $name, login = $login, password = $password, address = ${address?.addr}"
    }
}
