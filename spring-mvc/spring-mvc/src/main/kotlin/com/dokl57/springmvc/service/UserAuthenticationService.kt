package com.dokl57.springmvc.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class UserAuthenticationService {

    private val validUsers: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    init {
        validUsers["admin"] = "admin"
    }

    fun validateUser(username: String, password: String): Boolean {
        return validUsers[username] == password
    }
}
