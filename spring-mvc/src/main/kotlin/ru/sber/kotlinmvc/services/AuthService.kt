package ru.sber.kotlinmvc.services

import org.springframework.stereotype.Service

@Service
class AuthService {
    private val users : Map<String, String> = mapOf("admin" to "admin")

    fun authorize(username : String?, password : String?) : Boolean {
        if (username==null || password==null) {
            return false
        }
        return users.get(username) == password
    }

}