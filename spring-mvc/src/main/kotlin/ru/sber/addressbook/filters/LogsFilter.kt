package ru.sber.addressbook.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

@WebFilter(filterName = "LogsFilter")
class LogsFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) {
        request as HttpServletRequest
        println("Method: ${request.method}--> URL: ${request.requestURI}")
        filter.doFilter(request, response)
    }
}