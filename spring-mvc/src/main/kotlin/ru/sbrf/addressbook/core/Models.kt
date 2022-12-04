package ru.sbrf.addressbook.core

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class User(
    var id: Long = 0L,
    val login: String,
    val password: String
)

data class Employee(
    var id: Long = 0L,
    @field:NotBlank
    val firstName: String? = null,
    @field:NotBlank
    val lastName: String? = null,
    val patronymic: String? = null,
    val address: String? = null,
    val phone: String? = null,
    @field:Email
    val email: String? = null,
)
