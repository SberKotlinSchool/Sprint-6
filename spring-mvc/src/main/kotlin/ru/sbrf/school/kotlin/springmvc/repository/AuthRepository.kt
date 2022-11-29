package ru.sbrf.school.kotlin.springmvc.repository

import ru.sbrf.school.kotlin.springmvc.entity.User

interface AuthRepository {
    fun isPermitted(user: User) : Boolean
}