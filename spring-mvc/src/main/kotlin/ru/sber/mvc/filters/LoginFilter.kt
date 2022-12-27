package ru.sber.mvc.filters

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
   urlPatterns = ["/app/*", "/api/*"]
)
class LoginFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {

        if (p0 is HttpServletRequest) {
            if (p0.cookies.isNullOrEmpty() || p0.cookies.none { cookie ->
                    cookie.name.equals("auth") && LocalDateTime.parse(cookie.value).isBefore(LocalDateTime.now()) } )
                (p1 as HttpServletResponse).sendRedirect("/login")
            else
                p2?.doFilter(p0, p1)
        }
    }
}