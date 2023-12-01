package ru.shadowsith.addressbook.repositories

import org.springframework.stereotype.Repository
import ru.shadowsith.addressbook.dto.User

@Repository
class UserTempRepository : UserRepository {
    private val users = listOf(User(1, "aleks", "qwerty"))

    override fun find(login: String, password: String): User? {
        return users.find { it.login == login && it.password == password }
    }
}