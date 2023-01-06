package ru.morningcake.addressbook.filter

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/*"], filterName = "LogFilter")
@Order(1)
class LogFilter : Filter {
    private val logger = KotlinLogging.logger {}

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        logger.info { "${servletRequest.method} ${servletRequest.requestURI}" }
        filterChain!!.doFilter(request, response)
    }

}