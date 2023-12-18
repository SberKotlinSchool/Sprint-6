package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

@WebFilter("/app/*")
class AuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        val cookies = request.cookies
        if (cookies == null || cookies.find { it.name == "auth" } == null) {
            println("no cookie AuthFilter")
            println(request.method)

            response.sendRedirect("/login")
        } else {
            filterChain.doFilter(request, response)
        }
    }
}