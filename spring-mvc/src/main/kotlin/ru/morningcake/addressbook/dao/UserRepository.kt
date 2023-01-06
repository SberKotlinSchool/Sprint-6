package ru.morningcake.addressbook.dao

import org.springframework.stereotype.Repository
import ru.morningcake.addressbook.entity.User
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {

    private var users = ConcurrentHashMap(
        mapOf ("user" to User(name = "Юзверь", login = "user", password = "User@1"))
    )

    fun getUser(login : String) : User? {
        return users.getOrDefault(login, null)
    }
}