package io.vorotov.diary.services

import org.springframework.stereotype.Service
import io.vorotov.diary.models.UserInfo
import io.vorotov.diary.repository.UsersRepository

@Service
class UsersService (private val repository: UsersRepository) {
    fun check_users(user: UserInfo) = repository.isAuthenticated(user)


}