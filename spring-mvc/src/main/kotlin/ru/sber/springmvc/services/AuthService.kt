package ru.sber.springmvc.services

import org.springframework.stereotype.Service
import ru.sber.springmvc.repository.LoginPass

@Service
class AuthService {
    fun checkUserCred(userData: LoginPass): Boolean {
        if (userData.localDB[userData.userName] != null && userData.localDB[userData.userName] == userData.password) {
            return true
        }
        return false
    }
}