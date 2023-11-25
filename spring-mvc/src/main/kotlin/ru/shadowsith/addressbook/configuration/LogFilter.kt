package ru.shadowsith.addressbook.configuration

import mu.KLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Order(1)
@Component
class LogFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) {
        request as HttpServletRequest
        response as HttpServletResponse
        logger.info("${request.method} ${request.requestURI}")
        filter.doFilter(request, response)
    }

    companion object : KLogging()
}