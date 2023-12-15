package com.example.adresbook

import com.example.adresbook.model.BookRecord
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class AddressBookApplication

fun main(args: Array<String>) {
    runApplication<AddressBookApplication>(*args)
}
