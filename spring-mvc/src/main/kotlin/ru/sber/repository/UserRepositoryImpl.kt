package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepositoryImpl : UserRepository {
    private var users = ConcurrentHashMap<Long, User>()
    private var currentId = 0L

    override fun getByLogin(login: String): User? {
        return users
            .filter { login == it.value.login }
            .map { it.value }
            .firstOrNull()
    }

    override fun createUser(user: User) {
        users[currentId++] = user
    }

    override fun deleteByLogin(login: String) {
        users.remove(users
            .filter { login == it.value.login }
            .map { it.key }
            .firstOrNull())
    }
}