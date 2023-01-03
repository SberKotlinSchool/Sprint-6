package ru.sber.mvc.data

import java.time.LocalDate

data class Contact(
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String? = "",
    val birthDate: LocalDate? = null,
    val phoneNumber: String = "",
    val email: String = ""
)
