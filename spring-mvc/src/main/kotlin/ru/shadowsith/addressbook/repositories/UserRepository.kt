package ru.shadowsith.addressbook.repositories

import ru.shadowsith.addressbook.dto.User

interface UserRepository {
    fun find(login: String, password: String): User?
}