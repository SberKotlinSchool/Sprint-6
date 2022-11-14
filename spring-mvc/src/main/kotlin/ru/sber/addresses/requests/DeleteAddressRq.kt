package ru.sber.addresses.requests

/**
 * Запрос на удаление записи в адресной книге
 */
data class DeleteAddressRq(
    /**
     * ID записи
     */
    val id: Long
)