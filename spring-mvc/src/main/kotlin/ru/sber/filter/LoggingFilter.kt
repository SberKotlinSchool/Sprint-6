package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter

@WebFilter("/*")
class LoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val start = System.currentTimeMillis()
        filterChain.doFilter(request, response)
        val end = System.currentTimeMillis()
        val dur = end - start
        logger.info("Request to ${request.requestURI} took $dur ms")
    }
}