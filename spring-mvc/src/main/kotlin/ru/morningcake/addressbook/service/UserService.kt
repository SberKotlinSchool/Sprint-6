package ru.morningcake.addressbook.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.morningcake.addressbook.dao.UserRepository
import ru.morningcake.addressbook.entity.User

@Service
class UserService @Autowired constructor (private val repository : UserRepository) {

    fun checkAuth(login : String, password : String) : Boolean {
        val user = repository.getUser(login)
        return (user != null && user.password == password)
    }

    fun getUser(login : String) : User? {
        return repository.getUser(login)
    }
}