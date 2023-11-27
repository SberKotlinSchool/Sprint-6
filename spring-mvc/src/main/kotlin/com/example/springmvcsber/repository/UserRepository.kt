package com.example.springmvcsber.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val users = ConcurrentHashMap<String, String>()

    @Value("\${admin.username}")
    private val admin: String = ""

    @Value("\${admin.password}")
    private val password: String = ""

    init {
        users[admin] = password
    }

    fun get(login: String) = users[login]
}