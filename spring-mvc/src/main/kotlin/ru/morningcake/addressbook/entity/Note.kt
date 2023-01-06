package ru.morningcake.addressbook.entity

import java.util.*

data class Note constructor(
    var id: UUID,
    var name: String,
    var phone : String,
    var address : String,
    var comment : String
    )