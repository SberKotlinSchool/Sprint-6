package ru.sber.spring.mvc.model

data class ErrorMessage(
    val status: Int?,
    val message: String?
)