package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory

@WebFilter("/*")
class LoggingFilter : Filter {
    private companion object {
        private val LOGGER = LoggerFactory.getLogger(LoggingFilter::class.java)
    }

    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?, filterChain: FilterChain?
    ) {
        LOGGER.info(
            "${(servletRequest as HttpServletRequest).method}: auth: ${
                servletRequest.cookies?.any { it.name == "auth" }
            }"
        )
        filterChain?.doFilter(servletRequest, servletResponse)
    }
}