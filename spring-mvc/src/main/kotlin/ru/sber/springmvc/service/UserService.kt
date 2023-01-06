package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.model.User
import ru.sber.springmvc.repository.UserRepository

@Service
class UserService(private val repository: UserRepository) {
    fun check(user: User): Boolean = repository.get(user.login)?.equals(user.password) ?: false
}