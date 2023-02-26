package ru.sber.filter

import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import java.time.LocalDateTime
import javax.servlet.annotation.WebFilter

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