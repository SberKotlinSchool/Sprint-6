package com.sbuniver.homework.servlet

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest


@WebFilter("*")
@Order(1)
class LoggingFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        logger.info { "Request to ${httpRequest.requestURI} from ${httpRequest.remoteAddr} via ${httpRequest.method}-method" }
        chain?.doFilter(request, response)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}