package ru.sber.springmvc.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/api/v1/*"],
    servletNames = ["SpringMvcApplication"])
class ApiFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val rq = request as HttpServletRequest
        val rs = response as HttpServletResponse

        val cookies = rq.cookies


        chain.doFilter(rq,rs)
    }
}