package ru.sbrf.school.kotlin.springmvc.service

import org.springframework.stereotype.Service
import ru.sbrf.school.kotlin.springmvc.entity.User
import ru.sbrf.school.kotlin.springmvc.repository.AuthRepository

@Service
class FakeAuthService(val authRepository: AuthRepository) : AuthService {
    override fun isPermitted(user: User) = authRepository.isPermitted(user)
}