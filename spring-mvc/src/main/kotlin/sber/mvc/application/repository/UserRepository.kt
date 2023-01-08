package sber.mvc.application.repository

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val users = ConcurrentHashMap(mutableMapOf("admin" to "admin"))

    fun checkCredentials(login: String, password: String): Boolean {
        if (users.contains(login))
            return users[login]==password
        return false
    }
}