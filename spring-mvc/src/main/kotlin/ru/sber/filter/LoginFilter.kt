package ru.sber.filter

import mu.KotlinLogging
import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val COOKIE_AUTH_OVERDUE_MINUTES = 1L

@WebFilter(urlPatterns = ["/app/*", "/api/*"], servletNames = ["ApiController", "AppController"])
class LoginFilter : Filter {

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val request = servletRequest as HttpServletRequest
        log.warn { "request: ${request.method} ${request.requestURI}" }

        val auth = request.cookies.find { it.name == "auth" }
        val isCookiesAuthAndNotOverdue =
            auth != null && LocalDateTime.parse(auth.value) > LocalDateTime.now()
                .minusMinutes(COOKIE_AUTH_OVERDUE_MINUTES)

        if (isCookiesAuthAndNotOverdue) {
            filterChain.doFilter(servletRequest, servletResponse)
        } else {
            (servletResponse as HttpServletResponse).sendRedirect("/login")
        }
    }

    companion object {
        val log = KotlinLogging.logger {}
    }
}