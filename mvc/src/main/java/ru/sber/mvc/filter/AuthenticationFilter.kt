package ru.sber.mvc.filter

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import java.time.Instant
import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*"])
@Order(1)
class AuthenticationFilter : Filter {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        val cookies = request.cookies
        for (cookie in cookies) {
            if (cookie.name == "auth" && LocalDateTime.parse(cookie.value) < LocalDateTime.now()) {
                logger.info("auth cookie is true ${LocalDateTime.parse(cookie.value)} ${LocalDateTime.now()}")
                p2!!.doFilter(request, response)
                return
            }
        }

        p2!!.doFilter(request, response)
        logger.info("Client doesn't auth")
        response.sendRedirect("http://localhost:8080/login")
    }
}