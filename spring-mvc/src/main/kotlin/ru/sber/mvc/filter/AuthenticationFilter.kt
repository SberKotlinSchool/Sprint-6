package ru.sber.mvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
@WebFilter(urlPatterns = ["/app/*", "/api/*"], filterName = "AuthenticationFilter")
@Order(2)
class AuthenticationFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse
        val cookie = req.cookies?.firstOrNull { it.name.equals("auth") }
        if (cookie != null &&
            LocalDateTime.parse(cookie.value, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                .isBefore(LocalDateTime.now())
        ) {
            chain!!.doFilter(request, response)
        } else {
            val loginURI: String = request.contextPath + "/login"
            res.sendRedirect(loginURI)
        }
    }

}