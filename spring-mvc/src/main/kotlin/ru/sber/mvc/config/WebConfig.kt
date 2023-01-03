package ru.sber.mvc.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan("ru.sber.mvc")
@ServletComponentScan
class WebConfig : WebMvcConfigurer {

}