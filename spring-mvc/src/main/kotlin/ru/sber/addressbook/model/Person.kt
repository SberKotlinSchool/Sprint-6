package ru.sber.addressbook.model

data class Person(
    var id: Long? = null,
    var name: String = "",
    var city: String = ""
)