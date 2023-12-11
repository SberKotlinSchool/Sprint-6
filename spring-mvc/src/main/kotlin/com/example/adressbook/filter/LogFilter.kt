package com.example.adressbook.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import mu.KLogging

@WebFilter("/*")
class LogFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        logger.info("Запрос от ${request?.remoteHost}:${request?.remotePort}")
        chain?.doFilter(request, response)
    }

    companion object : KLogging()
}