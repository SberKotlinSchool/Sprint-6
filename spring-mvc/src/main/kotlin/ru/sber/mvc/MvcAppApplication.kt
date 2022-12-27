package ru.sber.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class MvcAppApplication

fun main(args: Array<String>) {
	runApplication<MvcAppApplication>(*args)
}
