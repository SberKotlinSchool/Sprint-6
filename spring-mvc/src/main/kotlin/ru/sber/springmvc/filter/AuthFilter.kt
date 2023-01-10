package ru.sber.springmvc.filter

import java.util.*
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns =["/","/users","/user/**"])
class AuthFilter : Filter {

    private lateinit var context: ServletContext

    override fun init(filterConfig: FilterConfig) {
        super.init(filterConfig)
        context = filterConfig.servletContext
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {

        val httpServletRequest = (servletRequest as HttpServletRequest)
        val cookieValue = httpServletRequest.cookies
                ?.firstOrNull { it.name == "auth" }
                ?.value

        if (cookieValue == null || cookieValue.toLong() >= Date().time) {
            (servletResponse as HttpServletResponse).sendRedirect("/login")
        } else {
            filterChain.doFilter(servletRequest, servletResponse)
        }
    }
}