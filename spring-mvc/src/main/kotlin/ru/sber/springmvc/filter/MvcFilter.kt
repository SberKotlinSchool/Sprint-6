package ru.sber.springmvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class MvcFilter : Filter {
    companion object {
        private val log = LoggerFactory.getLogger(MvcFilter::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest) {
            val authCookie = request.cookies?.firstOrNull { it.name == "auth" }
            if (authCookie != null &&
                LocalDateTime.parse(authCookie.value).isBefore(LocalDateTime.now())
            ) {
                chain.doFilter(request, response)
            } else {
                (response as HttpServletResponse).addCookie(Cookie("redirect", request.requestURL.toString()))
                response.sendRedirect("/login")
            }
        }
    }
}
