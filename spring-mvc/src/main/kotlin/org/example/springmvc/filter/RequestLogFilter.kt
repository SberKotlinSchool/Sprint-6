package org.example.springmvc.filter

import org.slf4j.Logger
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"])
class RequestLogFilter(private val logger: Logger) : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger.info("${request.method} > ${request.requestURL}")
        filterChain.doFilter(request, response)
    }
}