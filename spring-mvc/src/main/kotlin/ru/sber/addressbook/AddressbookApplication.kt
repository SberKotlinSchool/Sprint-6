package ru.sber.addressbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class AddressbookApplication

fun main(args: Array<String>) {
	runApplication<AddressbookApplication>(*args)
}
