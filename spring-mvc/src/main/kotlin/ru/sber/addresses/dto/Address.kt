package ru.sber.addresses.dto

/**
 * Адрес
 */
data class Address(
    /**
     * ID записи
     */
    val id: Long,

    /**
     * ФИО контакта
     */
    val fullName: String,

    /**
     * Почтовый адрес
     */
    val postAddress: String?,

    /**
     * Номер телефона
     */
    val phoneNumber: String?
)