package ru.shadowsith.addressbook.dto

import java.time.LocalDateTime

data class Record(
    val guid: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val createDataTime: LocalDateTime = LocalDateTime.now()
)
