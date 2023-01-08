package sber.mvc.application.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sber.mvc.application.repository.UserRepository

@Service
class UserService(@Autowired val userRepository: UserRepository) {
    fun checkCredentials(login: String, password: String): Boolean {
        return userRepository.checkCredentials(login, password)
    }
}