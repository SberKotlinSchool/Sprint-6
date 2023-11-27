package ru.sber.dto

data class AddressDto(
        var id: Long?,
        val name: String
) {
    constructor(name: String) : this(name = name, id = null)
}