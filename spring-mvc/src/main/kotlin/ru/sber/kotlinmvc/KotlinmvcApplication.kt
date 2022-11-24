package ru.sber.kotlinmvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinmvcApplication

fun main(args: Array<String>) {
	runApplication<KotlinmvcApplication>(*args)
}
