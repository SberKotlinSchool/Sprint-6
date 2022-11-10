package ru.sber.filter

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
    filterName = "UserFilter"
)
class UserFilter : Filter {
    override fun doFilter(req: ServletRequest, resp: ServletResponse, filterChain: FilterChain?) {
        val request = req as HttpServletRequest
        val response = resp as HttpServletResponse
        if (request.cookies == null || request.cookies.none { it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now() }) {
            response.sendRedirect("/login")
        } else {
            filterChain?.doFilter(req, resp)
        }
    }
}