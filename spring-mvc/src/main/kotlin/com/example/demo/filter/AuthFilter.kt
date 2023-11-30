package com.example.demo.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class AuthFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val cookies = request.cookies
        if (cookies?.find { it.name == "auth" } == null) {
            return response.sendRedirect("/login")
        }
        chain.doFilter(request, response)
    }
}