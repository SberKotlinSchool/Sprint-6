package io.vorotov.diary.repository

import org.springframework.stereotype.Repository
import io.vorotov.diary.models.UserInfo
import java.util.concurrent.ConcurrentHashMap

@Repository
class UsersRepository {

        val users: ConcurrentHashMap<String, String> = ConcurrentHashMap(mapOf("user1" to "1", "user2" to "2"))

        fun isAuthenticated(user: UserInfo) = users[user.username] == user.password

}
