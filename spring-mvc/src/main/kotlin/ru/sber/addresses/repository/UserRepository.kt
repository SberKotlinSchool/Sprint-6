package ru.sber.addresses.repository

import org.springframework.stereotype.Repository
import ru.sber.addresses.dto.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val users = ConcurrentHashMap(mapOf("user" to "user", "admin" to "admin"))

    fun checkUser(user: User) = users[user.login] == user.password
}