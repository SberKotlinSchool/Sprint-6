package ru.sber.mvc.domain


data class DomainUser(val username: String, val password: String)

data class DomainRecord(
    val name: String,
    val phone: String,
    val address: String,
    val description: String = "",
    val id: Long? = null,
) {
    companion object {

        val EMPTY: DomainRecord
            get() = DomainRecord("", "", "")
    }
}
