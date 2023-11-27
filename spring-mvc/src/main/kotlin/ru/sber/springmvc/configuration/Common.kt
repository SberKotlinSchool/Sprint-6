package ru.sber.springmvc.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sber.springmvc.filter.LoggerFilter

@Configuration
class Common {
    @Bean
    fun logger(): Logger {
        return LoggerFactory.getLogger(LoggerFilter::class.java)
    }
}