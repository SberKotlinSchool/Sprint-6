package ru.sber.servlet.filter

import mu.KLogging
import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogFilter : HttpFilter() {

    companion object : KLogging()

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        logger.info("Request received: method: ${request.method}, " +
                "requestURI: ${request.requestURI}, " +
                "cookie: ${request.cookies?.find { it.name == "auth" }?.value}")

        chain.doFilter(request, response)
    }
}