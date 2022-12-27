package ru.sber.mvc.repositories

import org.springframework.stereotype.Repository
import ru.sber.mvc.models.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class LoginCredsRepo : LoginCredsRepoCheckable {

    val credentials = ConcurrentHashMap(mapOf("user" to "pass"))

    override fun isAccessAllowed(user: User): Boolean {
        return credentials[user.login] == user.password
    }
}