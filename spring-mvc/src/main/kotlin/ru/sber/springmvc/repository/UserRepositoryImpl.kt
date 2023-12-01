package ru.sber.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sber.springmvc.model.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepositoryImpl : UserRepository {
    private val users = ConcurrentHashMap(mapOf("admin" to "admin"))

    override fun exists(user: User): Boolean {
        return users[user.login] == user.password
    }
}