package ru.sber.spring.mvc.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {

        val httpRequest = request as HttpServletRequest

        val denied = httpRequest.cookies
            ?.filter { it.name == "auth" }
            ?.map { it.value }
            ?.map { LocalDateTime.parse(it) }
            ?.any { it < LocalDateTime.now() } ?: true

        if (denied) {
            with((response as HttpServletResponse)) {
                sendRedirect("/login")
            }
        }

        chain.doFilter(request, response)
    }
}