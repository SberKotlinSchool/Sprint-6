package ru.sber.service

import org.springframework.stereotype.Service
import ru.sber.model.User
import ru.sber.repository.UserRepository

@Service
class AuthService(private val repository: UserRepository) {
    fun authenticate(user: User): Boolean {
        val userInDb = getUser(user)
        return (userInDb == null || userInDb.password != user.password)
    }

    private fun getUser(user: User) =
        repository.findByName(user.name)

}