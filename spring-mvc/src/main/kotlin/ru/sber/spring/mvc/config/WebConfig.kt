package ru.sber.spring.mvc.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.spring.mvc.auth.AuthService
import ru.sber.spring.mvc.filter.AuthFilter
import ru.sber.spring.mvc.filter.LoggerFilter
import ru.sber.spring.mvc.servlet.AuthServlet


@Configuration
@ComponentScan
class WebConfig {

    @Bean
    fun authServlet(authService: AuthService): ServletRegistrationBean<AuthServlet>? {
        val regBean = ServletRegistrationBean<AuthServlet>()
        regBean.order = 1
        regBean.addUrlMappings("/login")
        regBean.servlet = AuthServlet(authService)
        return regBean
    }

    @Bean
    fun loggerFilter(): FilterRegistrationBean<LoggerFilter>? {
        val regBean = FilterRegistrationBean<LoggerFilter>()
        regBean.order = 1
        regBean.filter = LoggerFilter()
        return regBean
    }

    @Bean
    fun authFilter(): FilterRegistrationBean<AuthFilter>? {
        val regBean = FilterRegistrationBean<AuthFilter>()
        regBean.order = 2
        regBean.addUrlPatterns("/book/*", "/rest/book/*")
        regBean.filter = AuthFilter()
        return regBean
    }
}
