package ru.company

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.company")
@ServletComponentScan("ru.company.servlets", "ru.company.filter")
class Configuration