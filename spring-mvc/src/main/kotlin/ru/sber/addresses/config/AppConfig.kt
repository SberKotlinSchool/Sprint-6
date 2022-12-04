package ru.sber.addresses.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.addresses")
@ServletComponentScan("ru.sber.addresses.servlets", "ru.addresses.sber.filters")
class AppConfig