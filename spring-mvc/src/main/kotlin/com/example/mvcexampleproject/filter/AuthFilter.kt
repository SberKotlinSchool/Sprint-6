package com.example.mvcexampleproject.filter

import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/rest/*"])
class AuthFilter : HttpFilter() {
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