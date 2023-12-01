package ru.sber.springmvc.model

data class Person (
    val id: Int = 0,
    val name: String = "",
    val address: String? = "",
    val phone: String? = ""
)