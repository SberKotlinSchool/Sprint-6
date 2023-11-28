package ru.sber.model.user

interface UserService {
  fun getUser(username: String, password: String): UserDTO?
}