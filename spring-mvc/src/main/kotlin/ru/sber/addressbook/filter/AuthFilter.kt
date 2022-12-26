package ru.sber.addressbook.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val SESSION_TIME = 5L

@WebFilter(urlPatterns = ["/persons/*"])
class AuthFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        request as HttpServletRequest
        val cookies = request.cookies
        val validCookie = cookies
            .any {
                it.name == "auth" && LocalDateTime.parse(it.value).plusMinutes(SESSION_TIME) > LocalDateTime.now()
            }
        if (!cookies.isNullOrEmpty() && validCookie) {
            filterChain.doFilter(request, response)
        } else {
            (response as HttpServletResponse).sendRedirect("/login")
        }
    }
}