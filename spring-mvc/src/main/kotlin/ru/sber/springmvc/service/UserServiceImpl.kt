package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.model.User
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Service
class UserServiceImpl : UserService {
    private val userData = ConcurrentHashMap<Long, User>()

    @PostConstruct
    private fun init() {
        val user = User("name", "admin", "admin")
        user.id = 1
        saveUser(user)
    }

    override fun getUserList() : List<User> = userData.map { it.value }.toList()

    override fun findById(id: Long): Optional<User> = userData.let {
        val user = it[id]
        if (user != null) Optional.of(user) else Optional.empty()
    }

    override fun findByLogin(login: String): Optional<User> =
        userData.map { it.value }
                .firstOrNull { it.login == login }
                .let { if (it != null) Optional.of(it) else Optional.empty() }

    override fun findByLoginAndPassword(login: String, password: String) : Optional<User> =
        userData.map { it.value }
            .firstOrNull { it.login == login && it.password == password }
            .let { if (it != null) Optional.of(it) else Optional.empty() }

    override fun saveUser(user: User) : User =
        user.also {
            user.state = "old"
            userData[user.id] = user
        }
}