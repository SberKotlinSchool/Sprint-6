package ru.sber.spring.mvc.auth

interface AuthService {
    fun authentication(user: String, password: String): Boolean
}