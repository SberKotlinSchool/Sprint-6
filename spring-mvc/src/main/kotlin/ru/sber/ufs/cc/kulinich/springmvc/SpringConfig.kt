package ru.sber.ufs.cc.kulinich.springmvc

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@ComponentScan("ru.sber.ufs.cc.kulinich.springmvc")
@ServletComponentScan("ru.sber.ufs.cc.kulinich.springmvc.servlets",
    "ru.sber.ufs.cc.kulinich.springmvc.filters")
class SpringConfig : WebMvcConfigurer  {

}