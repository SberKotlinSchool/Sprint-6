package ru.sbrf.school.kotlin.springmvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class SpringMvcApplication
fun main(args: Array<String>) {
    runApplication<SpringMvcApplication>(*args)
}
