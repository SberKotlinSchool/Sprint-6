package ru.sber.springmvc.repository

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class UserRepository {
    private val users = ConcurrentHashMap<String, String>()

    init {
        users["admin"] = "123456"
    }

    fun get(login: String) = users[login]
}