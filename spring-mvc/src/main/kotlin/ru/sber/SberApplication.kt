package ru.sber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class SberApplication

fun main(args: Array<String>) {
	runApplication<SberApplication>(*args)
}
