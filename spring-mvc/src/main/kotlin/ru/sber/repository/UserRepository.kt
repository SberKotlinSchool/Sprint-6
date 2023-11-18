package ru.sber.repository

import ru.sber.model.User

interface UserRepository {
    fun getByLogin(login: String): User?
    fun createUser(user: User)
    fun deleteByLogin(login: String)
}