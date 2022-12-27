package ru.sber.mvc.services

import ru.sber.mvc.models.User

interface LoginServiceCheckable {
    fun isAccessAllowed(user: User):Boolean
}