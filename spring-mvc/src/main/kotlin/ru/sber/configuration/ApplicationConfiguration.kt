package ru.sber.configuration

import ru.sber.filter.LogFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ServletComponentScan("ru/sber/servlet", "ru.sber.filter")
class ApplicationConfiguration {
    @Bean
    fun logger(): Logger = LoggerFactory.getLogger(LogFilter::class.java)
}