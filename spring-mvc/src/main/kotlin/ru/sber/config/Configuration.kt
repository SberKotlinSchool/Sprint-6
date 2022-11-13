package ru.sber.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan

@org.springframework.context.annotation.Configuration
@ComponentScan("ru.sber")
@ServletComponentScan("ru.sber.servlets", "ru.sber.filter")
class Configuration

