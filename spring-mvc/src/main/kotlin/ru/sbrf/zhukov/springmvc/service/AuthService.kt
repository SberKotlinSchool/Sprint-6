package ru.sbrf.zhukov.springmvc.service

import org.springframework.stereotype.Service

@Service
class AuthService {

    private val authorizedUsers = mapOf("user" to "password")
    private val authenticatedUsers = mutableSetOf<String>()

    fun authenticate(username: String, password: String): Boolean {
        return authorizedUsers[username] == password
    }

    fun isAuthenticated(token: String): Boolean {
        return authenticatedUsers.contains(token)
    }

    fun addAuthenticatedUser(token: String) {
        authenticatedUsers.add(token)
    }

    fun removeAuthenticatedUser(token: String) {
        authenticatedUsers.remove(token)
    }

}
