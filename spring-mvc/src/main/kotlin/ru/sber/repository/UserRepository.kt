package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val users = ConcurrentHashMap<String, String>()

    init {
        addUser(User("Anton", "123"))
    }

    fun addUser(user: User) {
        users[user.name] = user.password
    }

    fun findByName(name: String): User? = users[name]?.let { User(name, it) }

    fun deleteByName(name: String) {
        users.remove(name)
    }

}