package ru.sber.springmvc.filter

import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.Logger

@WebFilter(urlPatterns = ["/*"])
class LogFilter(private val logger: Logger) : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger.info("${request.method} > ${request.requestURL}")
        filterChain.doFilter(request, response)
    }
}