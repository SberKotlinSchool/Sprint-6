package ru.sber.springmvc.filter

import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import javax.servlet.Filter

class ApiFilter(private val testAuth: String) : Filter {
    companion object {
        private val log = LoggerFactory.getLogger(ApiFilter::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest) {
            val authHeader = request.getHeader("Authorization")
            if (authHeader != null && authHeader.equals("Basic $testAuth")) {
                chain.doFilter(request, response)
            } else {
                (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
                log.warn("Unauthorized request to ${request.requestURL}")
            }
        }
    }
}
