package ru.sber.mvc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.mvc.data.UserRepository
import java.time.LocalDateTime
import javax.servlet.http.Cookie


interface UserValidationService {

    fun validateUser(username: String, password: String): Boolean

    fun validateAuthCookie(authCookie: Cookie): Boolean
}

@Service
class UserValidationServiceImpl @Autowired constructor(
    private val userRepository: UserRepository
) : UserValidationService {

    override fun validateUser(username: String, password: String): Boolean =
        userRepository.users.any { user -> user.username == username && user.password == password }

    override fun validateAuthCookie(authCookie: Cookie): Boolean =
        authCookie.value != null && LocalDateTime.parse(authCookie.value).isBefore(LocalDateTime.now())
}
