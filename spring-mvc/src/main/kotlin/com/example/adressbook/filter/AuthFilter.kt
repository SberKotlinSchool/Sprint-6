package com.example.adressbook.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"]
)
class AuthFilter: Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request is HttpServletRequest && response is HttpServletResponse) {
            val cookies = request.cookies.orEmpty()
            if (cookies.any { it.name == "auth" && LocalDateTime.parse(it.value).isBefore(LocalDateTime.now()) }) {
                chain?.doFilter(request, response)
            } else {
                response.sendRedirect("/login")
            }
        }
    }
}