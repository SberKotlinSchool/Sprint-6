package ru.sber.springmvc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.springmvc.exception.AuthException
import ru.sber.springmvc.model.User
import ru.sber.springmvc.repository.UserRepository

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {
    override fun authenticate(user: User) {
        if (!userRepository.exists(user)) {
            throw AuthException()
        }
    }
}