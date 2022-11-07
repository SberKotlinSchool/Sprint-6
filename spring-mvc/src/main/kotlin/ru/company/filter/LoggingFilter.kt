package ru.company.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest


@WebFilter(
    urlPatterns = ["/*"],
    filterName = "LoggingFilter"
)
class LoggingFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        if (p0 is HttpServletRequest)
            println(
                "REQUEST: ${p0.remoteAddr}: ${p0.method} ${p0.requestURI}. Auth user: ${
                    p0.cookies?.any {
                        it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now()
                    }
                }"
            )
        p2?.doFilter(p0, p1)
    }
}