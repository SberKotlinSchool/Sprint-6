package ru.sber.addresses.requests

/**
 * Запрос на получение одной записи/всех записей в адресной книге
 */
data class GetAddressRq(
    /**
     * ID записи
     */
    val id: Long?
)