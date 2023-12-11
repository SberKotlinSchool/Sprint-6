package com.example.adressbook.exception

import org.springframework.http.HttpStatus

class BusinessException(
    val responseCode: HttpStatus,
    val errorMessage: String
) : RuntimeException(errorMessage)