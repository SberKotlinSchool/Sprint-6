package ru.sber.filter

import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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