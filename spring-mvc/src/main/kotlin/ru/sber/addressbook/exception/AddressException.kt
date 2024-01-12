package ru.sber.addressbook.exception

import org.springframework.http.HttpStatus

class AddressException(
    val responseCode: HttpStatus,
    val error: String
) : RuntimeException(error)