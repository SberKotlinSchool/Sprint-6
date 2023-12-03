package ru.sber.filter

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
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        request as HttpServletRequest
        if (isAuthenticated(request)) {
            chain!!.doFilter(request, response)
        } else {
            response as HttpServletResponse
            response.sendRedirect("/login")
        }
    }

    private fun isAuthenticated(request: HttpServletRequest): Boolean {
        return !request.cookies.isNullOrEmpty() && request.cookies.any { it.name == "auth1" && LocalDateTime.parse(it.value) > LocalDateTime.now() }
    }
}