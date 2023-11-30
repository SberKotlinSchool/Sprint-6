package com.example.demo.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@WebFilter(urlPatterns = ["/*"])
class LogFilter(private val logger: Logger = LoggerFactory.getLogger(LogFilter::class.java)) : HttpFilter() {

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        logger.info("${request.method} ${request.requestURI}")
        chain.doFilter(request, response)
    }
}