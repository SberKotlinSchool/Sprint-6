package ru.sbrf.zhukov.springmvc.filter

import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter("/*")
class LoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val startTime = System.currentTimeMillis()

        filterChain.doFilter(request, response)

        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        logger.info("Request to ${request.requestURI} took $duration ms")
    }
}
