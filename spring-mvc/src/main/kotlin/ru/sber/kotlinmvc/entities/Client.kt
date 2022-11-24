package ru.sber.kotlinmvc.entities

data class Client(val name: String, val address: String?, val phone: String?, val email: String? ) {
    lateinit var id: Integer
}