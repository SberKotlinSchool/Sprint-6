package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import ru.sber.service.AuthService

@WebFilter("/app/*")
class AuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authCookie = request.cookies.find { it.name == "auth" }
        if (authCookie == null) {
            response.sendRedirect("/login")
        } else {
            filterChain.doFilter(request, response)
        }
    }
}