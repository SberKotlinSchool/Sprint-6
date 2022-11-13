package ru.sber.model

data class Person(
    val id: Int? = 1,
    val fio: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val email: String? = null
)

data class User(
    val login: String,
    val password: String,
)