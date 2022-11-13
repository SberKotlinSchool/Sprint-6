package ru.sber.spring.mvc.filter

import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggerFilter: Filter {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        logger.info("Request from: ${httpRequest.requestURI}")

        val httpResponse = response as HttpServletResponse
        logger.info("Response with status: ${httpResponse.status}")

        chain.doFilter(request, response)
    }
}