package io.vorotov.diary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class DiaryApplication

fun main(args: Array<String>) {
	runApplication<DiaryApplication>(*args)
}
