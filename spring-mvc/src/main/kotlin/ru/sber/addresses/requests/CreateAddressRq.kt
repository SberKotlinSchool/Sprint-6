package ru.sber.addresses.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * Запрос на создание записи в адресной книге
 */
data class CreateAddressRq(
    /**
     * ФИО контакта
     */
    @field:NotEmpty
    val fullName: String? = null,

    /**
     * Почтовый адрес
     */
    val postAddress: String? = null,

    /**
     * Номер телефона
     */
    val phoneNumber: String? = null,

    /**
     * Адрес электронной почты
     */
    @field:Email
    val email: String? = null
)