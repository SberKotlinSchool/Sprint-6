package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication
@ServletComponentScan
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
