package ru.sber.addresses.services

import org.springframework.stereotype.Service
import ru.sber.addresses.dto.User
import ru.sber.addresses.repository.UserRepository

@Service
class UserService(private val repository: UserRepository) {
    fun checkUser(user: User) = repository.checkUser(user)
}