package ru.sber.servlet

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ServletComponentScan("ru.sber.servlet", "ru.sber.filter")
class ServletConfig