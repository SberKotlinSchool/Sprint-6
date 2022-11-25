package ru.sber.agadressbook.filters

import mu.KotlinLogging
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/addressbook/*"],
    filterName = "LogFilter"
)



class LogFilter : Filter {
    private val logger = KotlinLogging.logger {}
    private var logData = mutableListOf<String>()

    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        val logStr = "GET REQUEST: method == ${servletRequest.method},  requestURI == ${servletRequest.requestURI}"
        logData.add(logStr)
        logger.info { logStr }
        filterChain!!.doFilter(request, response)
    }
}