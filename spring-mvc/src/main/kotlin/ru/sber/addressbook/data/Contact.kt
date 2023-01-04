package ru.sber.addressbook.data

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class Contact(
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String? = "",
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate? = null,
    val phoneNumber: String = "",
    val email: String = ""
)
