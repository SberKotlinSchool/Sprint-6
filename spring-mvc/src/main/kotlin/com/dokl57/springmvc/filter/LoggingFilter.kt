package com.dokl57.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger

@WebFilter(urlPatterns = ["/*"])
class LoggingFilter(private val logger: Logger) : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request as HttpServletRequest
        response as HttpServletResponse
        logger.info("Session ${request.session.id}: processing request method ${request.method} on URI ${request.requestURI}, status: ${response.status}")
        chain.doFilter(request, response)
    }
}