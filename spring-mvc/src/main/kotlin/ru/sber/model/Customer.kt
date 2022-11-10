package ru.sber.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class Customer(
    val id: Int? = 1,
    @field:NotEmpty
    val fio: String? = null,
    val address: String? = null,
    val phone: String? = null,
    @field:Email
    val email: String? = null
)