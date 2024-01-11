package ru.sber.addressbook.dto

data class AddressModel(
    val name: String = "",
    val address: String = "",
    var id: Long = 0
)