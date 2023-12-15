package com.example.adresbook.filter

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
class AuthFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {

        if (p0 is HttpServletRequest) {
            if (p0.cookies.isNullOrEmpty() || p0.cookies.none { cookie ->
                    cookie.name.equals("auth") && LocalDateTime.parse(cookie.value).isBefore(LocalDateTime.now())
                })
                (p1 as HttpServletResponse).sendRedirect("/login")
            else
                p2?.doFilter(p0, p1)
        }
    }
}