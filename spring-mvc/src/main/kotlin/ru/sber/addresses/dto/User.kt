package ru.sber.addresses.dto

/**
 * Пользователь
 */
data class User(
    /**
     * Логин пользователя
     */
    val login: String,

    /**
     * Пароль
     */
    val password: String
)