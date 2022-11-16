package ru.sber.AddressBook.Filters

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"], filterName = "CookieFilter")
class CookieFilter: Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as? HttpServletRequest
        val response = p1 as? HttpServletResponse

        if (request?.cookies == null || request.cookies.get(0).name != "auth" ||
            LocalDateTime.parse(request.cookies.get(0).value) < LocalDateTime.now()) {
            print("Cookie check failed. Return to login page.")
            response?.sendRedirect("/login")
        } else {
            println("Cookie check success")
            p2?.doFilter(request, response)
        }
    }
}