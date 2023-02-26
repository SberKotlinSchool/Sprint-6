package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import java.time.LocalDateTime

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
@Order(2)
class AuthFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val time = request?.cookies?.find { it.name == "auth" }?.value
        if (time.isNullOrEmpty() || LocalDateTime.now() < LocalDateTime.parse(time)) {
            response?.sendRedirect("/login")
        } else {
            chain?.doFilter(request, response)
        }
    }
}