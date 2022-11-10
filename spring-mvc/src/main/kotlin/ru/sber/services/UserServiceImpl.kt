package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.model.User
import ru.sber.repository.UserRepository

@Service
class UserServiceImpl @Autowired constructor(val repository: UserRepository) : UserService {

    override fun checkUser(user: User): Boolean = repository.checkUser(user)

}