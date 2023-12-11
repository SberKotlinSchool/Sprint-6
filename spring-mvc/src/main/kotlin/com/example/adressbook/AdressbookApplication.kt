package com.example.adressbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
class AdressbookApplication

fun main(args: Array<String>) {
	runApplication<AdressbookApplication>(*args)
}
