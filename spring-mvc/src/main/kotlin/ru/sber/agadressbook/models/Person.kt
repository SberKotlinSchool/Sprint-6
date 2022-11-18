package ru.sber.agadressbook.models

data class Person(
    var id :Int = -1,
    var firstName: String,
    var phoneNumber: String = "",
    var address: String = ""
)
