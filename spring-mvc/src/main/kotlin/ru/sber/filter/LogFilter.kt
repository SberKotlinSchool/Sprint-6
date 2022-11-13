package ru.sber.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/*"],
    filterName = "LogFilter"
)
class LogFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        println(
            "Request received: method ${p0.method},   requestURI ${p0.requestURI}, has auth cookie:  ${
                p0.cookies?.any { it.name == "auth" }
            } "
        )
        p2!!.doFilter(request, response)
    }
}