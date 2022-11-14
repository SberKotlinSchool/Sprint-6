package ru.sber.addresses.requests

/**
 * Запрос на создание записи в адресной книге
 */
data class CreateAddressRq(
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