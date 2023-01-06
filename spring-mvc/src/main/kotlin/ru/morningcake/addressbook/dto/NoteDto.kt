package ru.morningcake.addressbook.dto

data class NoteDto constructor(
    var name: String,
    var phone : String,
    var address : String,
    var comment : String
    )