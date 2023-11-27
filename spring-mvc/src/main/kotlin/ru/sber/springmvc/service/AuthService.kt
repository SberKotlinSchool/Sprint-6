package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import ru.sber.springmvc.dto.User

@Service
class AuthService {
    private var database: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    init {
        database["test"] = "test"
    }

    fun verify(user: User): Boolean {
        return database.get(user.login).equals(user.password) ?: false
    }
}