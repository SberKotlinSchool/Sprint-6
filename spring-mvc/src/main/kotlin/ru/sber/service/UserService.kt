package ru.sber.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.model.User
import ru.sber.repository.UserRepository

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun isUserCorrect(user: User): Boolean {
        val userInRepository = userRepository.getByLogin(user.login)
        if (user.password == userInRepository?.password) {
            return true
        }
        return false
    }
}