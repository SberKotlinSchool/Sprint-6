package ru.sber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SberApplication

fun main(args: Array<String>) {
	runApplication<SberApplication>(*args)
}
