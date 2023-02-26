package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order

@WebFilter
@Order(1)
class LogFilter: HttpFilter() {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        log.info("Request received: method: ${request.method}, requestURI: ${request.requestURI}, cookie: ${request.cookies?.find { it.name == "auth" }?.value}")
        chain.doFilter(request, response)
    }
}