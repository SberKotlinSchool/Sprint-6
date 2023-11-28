package ru.sber.model.user

import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val repository: UserDAO) : UserService {
  override fun getUser(username: String, password: String): UserDTO? =
    repository.getUser(username, password)
}