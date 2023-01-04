package org.example.mvc.dto

data class AddressBookEntry(
    val name: String,
    val lastName: String,
    val phone: String,
    val email: String
) {
    var id: Long = 0
}