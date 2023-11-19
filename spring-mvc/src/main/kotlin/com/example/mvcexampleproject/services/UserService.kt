package com.example.mvcexampleproject.services

import com.example.mvcexampleproject.domain.User
import com.example.mvcexampleproject.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {
    fun check(user: User): Boolean = repository.get(user.login)?.equals(user.password) ?: false
}