package ru.shadowsith.addressbook.services

import org.springframework.stereotype.Service
import ru.shadowsith.addressbook.repositories.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun authentication(login: String, password: String): Boolean {
        val result = userRepository.find(login, password)
        return result?.let { true } ?: false
    }
}