package ru.sber.springmvc.domain

data class User(val id: Long, val role: String, val name: String, val password: String, var enabled: Boolean)
