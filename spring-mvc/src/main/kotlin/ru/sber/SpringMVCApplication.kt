package ru.sber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringMVCApplication

fun main(args: Array<String>) {
	runApplication<SpringMVCApplication>(*args)
}
