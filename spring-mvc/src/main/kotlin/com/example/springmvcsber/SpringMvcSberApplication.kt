package com.example.springmvcsber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class SpringMvcSberApplication

fun main(args: Array<String>) {
	runApplication<SpringMvcSberApplication>(*args)
}
