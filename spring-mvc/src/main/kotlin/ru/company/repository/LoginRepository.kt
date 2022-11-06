package ru.company.repository

import org.springframework.stereotype.Repository
import ru.company.model.Credential
import java.util.concurrent.ConcurrentHashMap

interface LoginRepository {
    fun checkCredentials(credential: Credential): Boolean
}

@Repository
class LoginRepositoryImpl : LoginRepository {
    private val credentials = ConcurrentHashMap(mapOf("inna" to "password1", "ivan" to "password2"))

    override fun checkCredentials(credential: Credential) = credentials[credential.login] == credential.password
}