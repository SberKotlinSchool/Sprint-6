package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val users = ConcurrentHashMap(mapOf("test" to "test", "admin" to "admin"))

    fun checkUser(user: User) = users[user.login] == user.password
}