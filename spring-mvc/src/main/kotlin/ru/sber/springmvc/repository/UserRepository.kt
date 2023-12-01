package ru.sber.springmvc.repository

import ru.sber.springmvc.model.User

interface UserRepository {
    fun exists(user: User): Boolean
}