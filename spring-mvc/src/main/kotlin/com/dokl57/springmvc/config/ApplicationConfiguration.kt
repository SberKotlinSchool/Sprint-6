package com.dokl57.springmvc.config

import com.dokl57.springmvc.filter.LoggingFilter
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.slf4j.Logger

@Configuration
@ServletComponentScan("com/dokl57/springmvc/servlet", "com.dokl57.springmvc.filter")
class ApplicationConfiguration {
    @Bean
    fun logger(): Logger = LoggerFactory.getLogger(LoggingFilter::class.java)
}