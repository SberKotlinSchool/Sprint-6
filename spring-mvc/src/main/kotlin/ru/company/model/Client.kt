package ru.company.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class Client(
    val id: Int? = 1,
    @field:NotEmpty
    val fio: String? = null,
    @field:NotEmpty
    val address: String? = null,
    val phone: String? = null,
    @field:Email
    val email: String? = null
)
