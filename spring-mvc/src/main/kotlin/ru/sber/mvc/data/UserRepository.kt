package ru.sber.mvc.data

import org.springframework.stereotype.Repository
import ru.sber.mvc.domain.User

interface UserRepository {

    val users: List<User>
}

@Repository
class UserRepositoryImpl : UserRepository {

    override val users = listOf(
        User("admin", "admin")
    )
}
