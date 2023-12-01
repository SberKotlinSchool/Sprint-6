package ru.sber.springmvc.service

import ru.sber.springmvc.model.User

interface UserService {
    fun authenticate(user: User)
}