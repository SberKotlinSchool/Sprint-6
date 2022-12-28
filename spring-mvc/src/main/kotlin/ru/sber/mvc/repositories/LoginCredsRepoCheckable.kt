package ru.sber.mvc.repositories

import ru.sber.mvc.models.User

interface LoginCredsRepoCheckable {
    fun isAccessAllowed(user: User):Boolean
}