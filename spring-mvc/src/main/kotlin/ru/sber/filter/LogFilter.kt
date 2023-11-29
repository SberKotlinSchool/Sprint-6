package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(1)
@WebFilter
class LogFilter: HttpFilter() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val logger = LoggerFactory.getLogger(javaClass)

        val httpRequest = request as HttpServletRequest
        logger.info("Request from: ${httpRequest.requestURI}")

        val httpResponse = response as HttpServletResponse
        logger.info("Response with status: ${httpResponse.status}")

        chain!!.doFilter(request, response)
    }
}