package sber.mvc.application.model

data class AddressBookEntry(
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val phone: String= "",
    val email: String= ""
) {
    var id: Long = 0L
}