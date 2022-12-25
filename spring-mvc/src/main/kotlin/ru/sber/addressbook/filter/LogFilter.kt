package ru.sber.addressbook.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging

@WebFilter(urlPatterns = ["/*"])
class LogFilter : Filter {
    private val logger = KotlinLogging.logger {}
    private var requestList = mutableListOf<String>()

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        response as HttpServletResponse
        request as HttpServletRequest
        val method = request.method
        val url = request.requestURL
        val message = "Request: $method URL: $url"
        logger.info { message }
        requestList.add(message)

        filterChain.doFilter(request, response)
    }
}