package ru.sber.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AuthService {
    private var users: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    init {
        users["guest"] = "guest32"
    }

    fun isLoginSuccess(login: String, password: String): Boolean {
        val correctPassword = users[login] ?: return false
        if (correctPassword != password) {
            return false
        }
        return true
    }
}