package ru.sber.addressbook.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/login"])
class LoginFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        response as HttpServletResponse
        request as HttpServletRequest
        val cookies = request.cookies
        var validCookie: Boolean = false
        if (cookies != null) {
            validCookie =
                cookies.any {
                    it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now()
                }
        }

        if (validCookie) {
            if (request.requestURI.contains("/login")) {
                response.sendRedirect("/persons")
            } else {
                filterChain.doFilter(request, response)
            }
        } else {
            request.getRequestDispatcher("/login").forward(request, response)
        }
    }
}