package com.example.mvcexampleproject.configuration

import com.example.mvcexampleproject.filter.LogFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {
    @Bean
    fun logger(): Logger = LoggerFactory.getLogger(LogFilter::class.java)
}