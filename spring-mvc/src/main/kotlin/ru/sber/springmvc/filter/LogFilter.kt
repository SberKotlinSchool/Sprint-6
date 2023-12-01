package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest

@WebFilter("/*")
class LogFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        println("${request.method}: ${request.requestURI}; auth:  ${request.cookies?.any { it.name == "auth" }}")
        p2?.doFilter(p0, p1)
    }
}