package ru.sber.springmvc.services

import org.springframework.stereotype.Service
import ru.sber.springmvc.repository.LoginPass

@Service
class AuthService {
    fun checkUserNamePass(userData: LoginPass): Boolean {
        return (checkUserName(userData) && userData.localDB[userData.userName] == userData.password)
    }

    fun checkUserName(userData: LoginPass): Boolean {
        return userData.localDB[userData.userName] != null
    }
}