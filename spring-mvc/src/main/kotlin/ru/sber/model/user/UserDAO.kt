package ru.sber.model.user

import java.util.concurrent.ConcurrentHashMap

interface UserDAO {
  fun clear()
  fun setUp(users: ConcurrentHashMap<String, UserDTO>)
  fun getUser(username: String, password: String): UserDTO?
}