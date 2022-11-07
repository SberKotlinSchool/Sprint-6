package ru.company.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.company.model.Credential
import ru.company.repository.LoginRepository

interface LoginService {
    fun authentication(credential: Credential): Boolean
}

@Service
class LoginServiceImpl @Autowired constructor(val repository: LoginRepository) : LoginService {

    override fun authentication(credential: Credential) = repository.checkCredentials(credential)

}