package ru.sber.addressbook.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan("ru.sber.addressbook")
@ServletComponentScan("ru.sber.addressbook.servlet", "ru.sber.addressbook.filter")
class WebConfig : WebMvcConfigurer {

}