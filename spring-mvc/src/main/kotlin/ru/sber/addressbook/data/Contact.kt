package ru.sber.addressbook.data

import java.time.LocalDate

data class Contact(
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String? = "",
    val birthDate: LocalDate? = null,
    val phoneNumber: String = "",
    val email: String = ""
)
