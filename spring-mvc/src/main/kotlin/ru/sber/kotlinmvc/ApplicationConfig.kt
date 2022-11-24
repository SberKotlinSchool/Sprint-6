package ru.sber.kotlinmvc

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.kotlinmvc")
@ServletComponentScan("ru.sber.kotlinmvc.filters", "ru.sber.kotlinmvc.servlets")
class ApplicationConfig