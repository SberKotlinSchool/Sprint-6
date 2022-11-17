package ru.sber.agadressbook.models

data class Person(
    val firstName: String,
    val phoneNumber: String = "",
    val address: String = ""
)
