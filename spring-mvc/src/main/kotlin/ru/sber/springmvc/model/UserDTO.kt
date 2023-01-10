package ru.sber.springmvc.model

import java.io.Serializable

class UserDTO : Serializable {
    var id: Long = 0
    var name: String? = null
    var login: String? = null
    var address: String? = null

    companion object {
        fun of(user: User): UserDTO {
            val result = UserDTO()
            result.id = user.id
            result.name = user.name
            result.login = user.login
            result.address = user.address?.addr
            return result
        }

        fun of(users: List<User>): List<UserDTO> = users.map { of(it) }
    }
}