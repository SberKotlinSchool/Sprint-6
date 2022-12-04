package ru.sber.addresses.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"], filterName = "LogFilter")
class LogFilter : Filter {
    override fun doFilter(rq: ServletRequest?, rs: ServletResponse?, filterChain: FilterChain?) {
        val request = rq as HttpServletRequest
        val response = rs as HttpServletResponse
        println(
            "Request received: method ${rq.method},   requestURI ${rq.requestURI}, has auth cookie:  ${
                rq.cookies?.any { it.name == "auth" }
            } "
        )
        filterChain!!.doFilter(request, response)
    }
}

