package ru.sber.addressbook.filter

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/app/*", "/api/*"])
@Order(2)
class AuthenticationFilter: Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse
        val cookie = req.cookies?.firstOrNull { it.name.equals("auth") }
        if (cookie != null &&
            LocalDateTime.parse(cookie.value, DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss"))
                .isBefore(LocalDateTime.now())
        ) {
            chain!!.doFilter(request, response)
        } else {
            val loginURI: String = request.contextPath + "/login"
            res.sendRedirect(loginURI)
        }
    }
}