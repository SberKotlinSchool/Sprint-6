package ru.sber.AddressBook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication

class AddressBookApplication

fun main(args: Array<String>) {
	runApplication<AddressBookApplication>(*args)
}
