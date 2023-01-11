package ru.morningcake.addressbook.validation

import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.exception.ValidationException

fun noteDtoValidation(dto : NoteDto?) {
    val builder = StringBuilder()
    if (dto == null) {
        builder.append("Необходимо тело запроса!")
    } else {
        if (dto.name.isBlank()) {
            builder.append("name - не должно быть пустым!\n")
        }
        if (dto.phone.isBlank()) {
            builder.append("phone - не должен быть пустым!\n")
        } else {
            val regex = """^[+]?\d{11,15}$""".toRegex()
            if (!regex.containsMatchIn(dto.phone)) {
                builder.append("phone - не соответствует формату: от 11 до 15 цифр без пробелов, тире, скобок и пр., м.б. плюс в начале\n")
            }
        }
        if (dto.address.isBlank()) {
            builder.append("address - не должен быть пустым!\n")
        }
    }
    if (builder.isNotEmpty()) {
        throw ValidationException(builder.toString())
    }
}