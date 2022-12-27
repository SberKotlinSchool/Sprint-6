package ru.sber.mvc.services

import org.springframework.stereotype.Service
import ru.sber.mvc.models.User
import ru.sber.mvc.repositories.LoginCredsRepoCheckable

@Service
class LoginService(val loginCredsRepo : LoginCredsRepoCheckable) : LoginServiceCheckable {
    override fun isAccessAllowed(user: User): Boolean {
        return loginCredsRepo.isAccessAllowed(user)
    }
}