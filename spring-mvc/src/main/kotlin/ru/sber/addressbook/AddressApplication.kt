package ru.sber.addressbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class AddressApplication

fun main(args: Array<String>) {
    runApplication<AddressApplication>(*args)
}