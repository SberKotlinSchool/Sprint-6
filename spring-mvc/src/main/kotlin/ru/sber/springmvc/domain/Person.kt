package ru.sber.springmvc.domain

data class Person(
    var id: Int = 0,
    var name: String = "",
    var address: String? = null,
    var phone: String? = null
)
