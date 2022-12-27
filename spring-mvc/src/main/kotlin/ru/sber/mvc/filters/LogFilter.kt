package ru.sber.mvc.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.core.annotation.Order

@WebFilter(
    urlPatterns = ["/*"]
)
@Order(2)
class LogFilter : Filter {

    private val logger = KotlinLogging.logger {}

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        p0 as HttpServletRequest
        p1 as HttpServletResponse

        logger.info { "Request - address: ${p0.remoteAddr} ${p0.method} ${p0.requestURL}; Cookie: ${p0.cookies?.any { it.name == "auth" }}; Response - status: ${p1.status}" }
        p2?.doFilter(p0, p1)
    }

}