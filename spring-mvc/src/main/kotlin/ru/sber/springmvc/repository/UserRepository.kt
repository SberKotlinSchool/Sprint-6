package ru.sber.springmvc.repository

import ru.sber.springmvc.domain.User

interface UserRepository {
    fun isExist(user: User): Boolean
}