package ru.sber.addresses.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * Адрес
 */
data class Address(
    /**
     * ID записи
     */
    val id: Long? = 1,

    /**
     * ФИО
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