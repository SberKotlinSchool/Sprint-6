package ru.shadowsith.addressbook.configuration

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@Order(2)
@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class AuthenticationFilter: Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        request as HttpServletRequest
        response as HttpServletResponse
        val cookie = request.cookies
        if (!cookie.isNullOrEmpty() && cookie.any { it.name == "auth" }) {
            val auth = cookie.find { it.name == "auth" }?.value
            if (LocalDateTime.parse(auth) < LocalDateTime.now()) {
                chain!!.doFilter(request, response)
            } else response.sendRedirect("/login")
        } else response.sendRedirect("/login")
    }
}