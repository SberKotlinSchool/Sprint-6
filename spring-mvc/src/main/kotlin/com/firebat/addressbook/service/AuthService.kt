package com.firebat.addressbook.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AuthService {
    private var database: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    init {
        database["q"] = "q"
    }

    fun isLoginSuccess(login: String, password: String): Boolean {
        val correctPassword = database[login] ?: return false
        if (correctPassword != password) {
            return false
        }
        return true
    }
}