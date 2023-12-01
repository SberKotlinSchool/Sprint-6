package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class AuthFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        val hasAuth = request.cookies?.any { it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now() } ?: false
        if (hasAuth) {
            p2?.doFilter(p0, p1)
        } else {
            response.sendRedirect("/login")
        }
    }
}