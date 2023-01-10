package io.vorotov.diary.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "Authentication"
)
class AuthenticationFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filter: FilterChain) =
        ((request as HttpServletRequest).cookies
            .firstOrNull { it.name == "auth" }
            ?.value
            .run {
                LocalDateTime.parse(this)
            }
            .plusMinutes(2)
            ?.isAfter(now())
            ?: false).let {
            if (!it) {
                (response as HttpServletResponse).sendRedirect("/login")
            } else
                filter.doFilter(request, response)
        }



}