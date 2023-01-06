package ru.sber.app.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@ServletComponentScan("ru.sber.app.servlets")
class WebConfig: WebMvcConfigurer{

    @Bean
    fun logFilter(): CommonsRequestLoggingFilter? {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(10000)
        filter.setIncludeHeaders(false)
        filter.setAfterMessagePrefix("REQUEST DATA : ")
        return filter
    }
}