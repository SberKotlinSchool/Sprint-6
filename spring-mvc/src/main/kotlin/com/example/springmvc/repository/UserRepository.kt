package com.example.springmvc.repository

import com.example.springmvc.model.User
import org.springframework.stereotype.Repository
import java.util.concurrent.CopyOnWriteArrayList

@Repository
class UserRepository {
    var users = CopyOnWriteArrayList(listOf(
        User("admin", "admin"))
    )

    fun checkUser(userName: String, password: String) =
        users.contains(User(userName, password))
}