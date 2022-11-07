package ru.company.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "AuthenticationFilter"
)
class AuthenticationFilter : Filter {
    override fun doFilter(req: ServletRequest, resp: ServletResponse, filterChain: FilterChain?) {
        val request = req as HttpServletRequest
        if (request.cookies == null || request.cookies.none { it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now() }) {
            (resp as HttpServletResponse).sendRedirect("/login")
        } else {
            filterChain?.doFilter(req, resp)
        }

    }
}