package ru.sber.AddressBook.Services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.AddressBook.Repositories.UserRepository

@Service
class AuthenticationService @Autowired constructor(val userRepository: UserRepository) {

    fun checkCredentials(login: String, password: String): Boolean {
        return userRepository.getUserPassword(login) == password
    }
}