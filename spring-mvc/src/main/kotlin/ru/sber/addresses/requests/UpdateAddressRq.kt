package ru.sber.addresses.requests

/**
 * Запрос на обновление записи в адресной книге
 */
data class UpdateAddressRq(
    /**
     * ID записи
     */
    val id: Long,

    /**
     * Адрес
     */
    val address: CreateAddressRq
)