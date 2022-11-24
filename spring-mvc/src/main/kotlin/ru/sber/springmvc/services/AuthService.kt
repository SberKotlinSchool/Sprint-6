package ru.sber.springmvc.services

import org.springframework.stereotype.Service
import ru.sber.springmvc.dto.User
import ru.sber.springmvc.utils.RequestUtils.Companion.usersDB

@Service
class AuthService {
    fun checkUserPass(userData: User): Boolean {
        return (userExists(userData) && usersDB[userData.userName] == userData.password)
    }

    fun userExists(userData: User): Boolean {
        return usersDB[userData.userName] != null
    }
}