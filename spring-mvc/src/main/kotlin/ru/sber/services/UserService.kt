package ru.sber.services

import ru.sber.model.User

interface UserService {
    fun checkUser(user: User): Boolean
}