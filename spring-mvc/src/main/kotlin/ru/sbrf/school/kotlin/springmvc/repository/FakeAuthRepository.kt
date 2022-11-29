package ru.sbrf.school.kotlin.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sbrf.school.kotlin.springmvc.entity.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class FakeAuthRepository : AuthRepository {
    val users = ConcurrentHashMap(mapOf("user" to "pass", "admin" to "megaPass"))

    override fun isPermitted(user: User) = users[user.username] == user.password
}