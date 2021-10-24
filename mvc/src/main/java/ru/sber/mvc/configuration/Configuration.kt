package ru.sber.mvc.configuration

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.mvc.service")
@ServletComponentScan("ru.sber.mvc.servlets", "ru.sber.mvc.filter")
open class Configuration
