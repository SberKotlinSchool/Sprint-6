package ru.sber.springmvc.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import java.time.Instant

@WebFilter(urlPatterns = ["/*"])
class LogFilter(private val logger: Logger) : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger.info("${request.method} > ${request.requestURL} (${Instant.now()})")
        filterChain.doFilter(request, response)
    }
}