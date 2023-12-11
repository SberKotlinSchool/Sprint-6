package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.domain.User
import ru.sber.springmvc.exception.UserAuthenticationException
import ru.sber.springmvc.repository.UserRepository

@Service
class UserServiceImpl(private val usesRepository: UserRepository) : UserService {
    override fun authenticate(user: User) {
        if (!usesRepository.isExist(user)) {
            throw UserAuthenticationException()
        }
    }
}