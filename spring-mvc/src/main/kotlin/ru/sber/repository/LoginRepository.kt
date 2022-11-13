package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.User
import java.util.concurrent.ConcurrentHashMap

interface LoginRepository {
    fun checkCredentials(user: User): Boolean
}

@Repository
class LoginRepositoryImpl : LoginRepository {
    private val users = ConcurrentHashMap(mapOf("user1" to "password1"))

    override fun checkCredentials(user: User) = users[user.login] == user.password
}