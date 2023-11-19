package com.example.mvcexampleproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class MvcExampleProjectApplication

fun main(args: Array<String>) {
	runApplication<MvcExampleProjectApplication>(*args)
}
