package ru.sber.config


import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber")
@ServletComponentScan("ru.sber.servlets", "ru.sber.filter")
class AppConfig