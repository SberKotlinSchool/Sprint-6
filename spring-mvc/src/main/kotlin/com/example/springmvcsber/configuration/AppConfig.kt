package com.example.springmvcsber.configuration

import com.example.springmvcsber.filter.LoggingFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun logger(): Logger = LoggerFactory.getLogger(LoggingFilter::class.java)
}