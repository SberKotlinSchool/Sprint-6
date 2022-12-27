package ru.sber.addressbook.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest

@WebFilter(filterName = "LogsFilter")
class LogsFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) {
        request as HttpServletRequest
        println("Method: ${request.method}--> URL: ${request.requestURI}")
        filter.doFilter(request, response)
    }
}