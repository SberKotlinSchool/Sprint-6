package ru.sbrf.school.kotlin.springmvc.service

import ru.sbrf.school.kotlin.springmvc.entity.User

interface AuthService {
    fun isPermitted(user: User):Boolean
}