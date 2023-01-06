package ru.sber.springmvc.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(urlPatterns = ["/app/*", "/rest/*"])
class AuthenticationFilter() :
    HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val allowed = !request.cookies.isNullOrEmpty() && (
                request.cookies
                    .firstOrNull { it.name == "authentication" }?.run {
                        LocalDateTime
                            .parse(value)
                            .plusMinutes(30)
                            .isAfter(LocalDateTime.now())
                    } ?: false)

        if (allowed) {
            filterChain.doFilter(request, response)
        } else {
            response.sendRedirect("/login")
        }
    }
}