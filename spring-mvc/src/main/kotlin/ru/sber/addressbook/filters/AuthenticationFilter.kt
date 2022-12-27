package ru.sber.addressbook.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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