package ru.sber.mvc.filter

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(2)
class LoggingFilter : Filter {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request: HttpServletRequest = p0 as HttpServletRequest
        val response: HttpServletResponse = p1 as HttpServletResponse

        logger.info("Logging Request {} : {}", request.method, request.requestURI)
        //p2!!.doFilter(request, response)
        logger.info("Logging Response {}", response.contentType)
        p2!!.doFilter(request, response)
    }
}
