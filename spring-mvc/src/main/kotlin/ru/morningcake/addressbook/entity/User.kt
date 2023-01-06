package ru.morningcake.addressbook.entity

data class User constructor(
    var name: String,
    val login : String,
    var password : String
    )