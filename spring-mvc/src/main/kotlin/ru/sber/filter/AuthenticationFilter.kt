package ru.sber.filter

import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/**", "/api/**"])
class AuthenticationFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val time = request.cookies?.find { it.name == "auth" }?.value
        if (time.isNullOrEmpty() || LocalDateTime.now() <= LocalDateTime.parse(time)) {
            response.sendRedirect("authError.html")
        } else {
            response.sendRedirect("${request.contextPath}/login")
        }
        chain.doFilter(request, response)
    }
}