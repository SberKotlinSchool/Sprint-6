package ru.sber.kotlinmvc.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import java.time.LocalDateTime
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/addressBook/*", "/rest/*"])
class AuthFilter : Filter {

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        if (request.cookies == null || request.cookies.none { it.name == "auth" && LocalDateTime.parse(it.value) < LocalDateTime.now() }) {
            val response = p1 as HttpServletResponse
            response.sendRedirect("/login")
        } else {
            p2?.doFilter(p0, p1)
        }
    }

}