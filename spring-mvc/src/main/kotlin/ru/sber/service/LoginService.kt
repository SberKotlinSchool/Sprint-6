package ru.sber.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.model.User
import ru.sber.repository.LoginRepository

interface LoginService {
    fun authentication(user: User): Boolean
}

@Service
class LoginServiceImpl @Autowired constructor(val repository: LoginRepository) : LoginService {

    override fun authentication(user: User) = repository.checkCredentials(user)

}