package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class LoggerFilter : Filter {
    companion object {
        private val log = LoggerFactory.getLogger(LoggerFilter::class.java)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        log.info("Request to ${(request as HttpServletRequest).requestURL}")
        chain?.doFilter(request, response)
    }

}
