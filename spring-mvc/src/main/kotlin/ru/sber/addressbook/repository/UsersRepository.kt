package ru.sber.addressbook.repository

import org.springframework.stereotype.Repository
import ru.sber.addressbook.models.UserInfo
import java.util.concurrent.ConcurrentHashMap

@Repository
class UsersRepository {
        val users: ConcurrentHashMap<String, String> = ConcurrentHashMap(mapOf("admin" to "admin"))
        fun user_to_map(user: UserInfo) = users[user.username] == user.password
}
