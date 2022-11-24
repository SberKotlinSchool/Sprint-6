package ru.sber.kotlinmvc.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

@WebFilter
class LoggingFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        println("Запрос по адресу: ${request.requestURI}")
        p2?.doFilter(p0, p1)
    }
}