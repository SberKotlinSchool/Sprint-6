package ru.sber.springmvc.service

import ru.sber.springmvc.domain.User

interface UserService {
    fun authenticate(user: User)
}