package ru.sber.mvc.domain

import java.util.UUID


data class User(val username: String, val password: String)

data class Record(
    val name: String,
    val phone: String,
    val address: String,
    val description: String = "",
    val id: String = UUID.randomUUID().toString(),
) {
    companion object {

        val EMPTY: Record
            get() = Record("", "", "")
    }
}
