package ru.sber.springmvc

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.springmvc.filters.AccessFilter
import ru.sber.springmvc.filters.LogFilter

@Configuration
@ServletComponentScan("ru.sber.springmvc")
class Config {

    @Bean
    fun accessFilter() =
        FilterRegistrationBean<AccessFilter>()
            .apply {
                filter = AccessFilter()
                urlPatterns = listOf("/api/*")
                order = 2
            }

    @Bean
    fun logFilter() =
        FilterRegistrationBean<LogFilter>()
            .apply {
                filter = LogFilter()
                order = 1
            }
}