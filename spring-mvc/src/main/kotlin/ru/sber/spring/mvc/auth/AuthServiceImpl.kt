package ru.sber.spring.mvc.auth

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AuthServiceImpl : AuthService {
    private val clients: ConcurrentHashMap<String, String> = ConcurrentHashMap(mapOf("sa" to "sa"))

    override fun authentication(user: String, password: String): Boolean {
        return clients[user] == password
    }
}