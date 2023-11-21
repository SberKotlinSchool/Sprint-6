package ru.sber.springmvc.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
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