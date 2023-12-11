package ru.sber.springmvc.repository

import org.springframework.stereotype.Repository
import ru.sber.springmvc.domain.User

@Repository
class UserRepositoryImpl : UserRepository {
    private companion object {
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
    }

    override fun isExist(user: User): Boolean {
        return user.login == LOGIN && user.password == PASSWORD
    }
}