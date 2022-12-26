package ru.sber.addressbook.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ServletComponentScan("ru.sber.addressbook.servlet", "ru.sber.addressbook.filter")
class WebServletConfig : WebMvcConfigurer {
}