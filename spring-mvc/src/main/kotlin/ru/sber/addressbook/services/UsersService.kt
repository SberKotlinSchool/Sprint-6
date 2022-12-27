package ru.sber.addressbook.services

import org.springframework.stereotype.Service
import ru.sber.addressbook.models.UserInfo
import ru.sber.addressbook.repository.UsersRepository

@Service
class UsersService (private val repository: UsersRepository) {
    fun check_users(user: UserInfo) = repository.user_to_map(user)


}