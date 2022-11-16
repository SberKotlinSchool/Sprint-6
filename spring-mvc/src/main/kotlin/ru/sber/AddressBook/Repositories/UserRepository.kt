package ru.sber.AddressBook.Repositories

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
    private val usersData: ConcurrentHashMap<String, String> = ConcurrentHashMap(
        mapOf(
            "test" to "test",
            "test2" to "test2"
        )
    )

    fun getUserPassword(login: String): String? = usersData[login]
}