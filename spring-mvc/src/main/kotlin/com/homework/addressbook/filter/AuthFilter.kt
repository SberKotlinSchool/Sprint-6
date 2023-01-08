package com.homework.addressbook.filter;

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(2)
class AuthFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val authCookie = (request as HttpServletRequest).cookies?.firstOrNull { it.name.equals("auth") }
        if (authCookie == null) {
            (response as HttpServletResponse).sendRedirect("/login")
        } else {
            val cookieValue = LocalDateTime.parse(authCookie.value)
            if (cookieValue > cookieValue.plus(10, ChronoUnit.MINUTES)) {
                (response as HttpServletResponse).sendRedirect("/login")
            } else chain?.doFilter(request, response)
        }

    }
}
