package ru.sber.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan("ru.sber")
@ServletComponentScan("ru.sber.servlet", "ru.sber.filter")
class WebConfig : WebMvcConfigurer {

}