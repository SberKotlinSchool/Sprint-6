package ru.sber.addressbook.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "AuthenticationFilter"
)
class AuthenticationFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) {
        val accessGranted = (request as HttpServletRequest).cookies
            .firstOrNull { it.name == "auth" }
            ?.value
            .run {
                LocalDateTime.parse(this)
            }
            .plusDays(1)
            ?.isAfter(LocalDateTime.now())
            ?: false

        if (!accessGranted) {
            (response as HttpServletResponse).sendRedirect("/login")
        } else
            filter.doFilter(request, response)





    }

}