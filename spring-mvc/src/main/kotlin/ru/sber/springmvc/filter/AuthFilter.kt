package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(urlPatterns = ["/api/*", "/app/*"])
class AuthFilter : Filter {
    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?, filterChain: FilterChain?
    ) {
        if ((servletRequest as HttpServletRequest).cookies?.any {
                it.name == "auth" && LocalDateTime.parse(it.value).isBefore(LocalDateTime.now())
            } == true) {
            filterChain?.doFilter(servletRequest, servletResponse)
        } else {
            (servletResponse as HttpServletResponse).sendRedirect("/login")
        }
    }
}