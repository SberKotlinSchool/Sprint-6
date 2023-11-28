package ru.sber.model.user

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserDAOImpl : UserDAO {

  val users: ConcurrentHashMap<String, UserDTO> =
    ConcurrentHashMap(mapOf("user1" to UserDTO("1", "user1", "pass1"),
      "user2" to UserDTO("2", "user2", "pass2"),
      "user3" to UserDTO("3", "user3", "pass3")))

  override fun clear() {
    users.clear()
  }

  override fun setUp(users: ConcurrentHashMap<String, UserDTO>) {
    this.users.putAll(users)
  }

  override fun getUser(username: String, password: String): UserDTO? =
    users.filter { it.key == username && it.value.password == password }.values.firstOrNull()
}