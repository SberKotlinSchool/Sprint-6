package ru.sber.ufs.cc.kulinich.springmvc.filters

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/*"])
@Order(1)
class LoggingFilter : Filter {
    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request  = p0 as HttpServletRequest
        val response  = p1 as HttpServletResponse

        logger.info("${request.method}\t${request.requestURI}")
        if (request.cookies != null)
            logger.info("${request.cookies.asSequence()}\n")

        logger.info("${response.status}")

        p2?.doFilter(p0, p1)

    }
}