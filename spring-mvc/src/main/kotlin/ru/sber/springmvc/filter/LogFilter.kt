package ru.sber.springmvc.filter

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

@WebFilter
class LogFilter : Filter {

    private lateinit var context: ServletContext
    override fun init(filterConfig: FilterConfig) {
        super.init(filterConfig)
        context = filterConfig.servletContext
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        context.log("Requested resource: ${(servletRequest as HttpServletRequest).requestURI}")
        filterChain.doFilter(servletRequest, servletResponse)
    }
}