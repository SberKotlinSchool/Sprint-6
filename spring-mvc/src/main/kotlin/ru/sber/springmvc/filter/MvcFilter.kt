package ru.sber.springmvc.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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
