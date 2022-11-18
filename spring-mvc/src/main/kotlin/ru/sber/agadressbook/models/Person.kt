package ru.sber.agadressbook.models

data class Person(
    var id :Int,
    var firstName: String,
    var phoneNumber: String = "",
    var address: String = ""
)
