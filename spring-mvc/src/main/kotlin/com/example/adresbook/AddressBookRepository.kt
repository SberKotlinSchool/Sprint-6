package com.example.adresbook

import com.example.adresbook.model.BookRecord
import org.springframework.stereotype.Component

@Component
class AddressBookRepository {
    val pseudoDataBase = mutableListOf(
        BookRecord(1, "Проспект Ленина 1", "1"),
        BookRecord(2, "Проспект Ленина 2", "2"),
        BookRecord(3, "Проспект Ленина 3", "3")
    )
}