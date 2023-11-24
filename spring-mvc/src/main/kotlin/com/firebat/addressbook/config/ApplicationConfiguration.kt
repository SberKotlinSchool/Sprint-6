package com.firebat.addressbook.config

import com.firebat.addressbook.filter.LogFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ServletComponentScan("com/firebat/addressbook/servlet", "com.firebat.addressbook.filter")
class ApplicationConfiguration {
    @Bean
    fun logger(): Logger = LoggerFactory.getLogger(LogFilter::class.java)
}