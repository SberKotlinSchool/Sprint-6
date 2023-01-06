package ru.morningcake.addressbook.dto.mapper

import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import java.util.*

fun noteFromDto(id : UUID, dto : NoteDto) : Note {
    return Note(id = id, name = dto.name, phone = dto.phone, address = dto.address, comment = dto.comment)
}