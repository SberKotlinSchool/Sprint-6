package ru.morningcake.addressbook.filter

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/app/*", "/api/*", "/logout"], filterName = "AuthFilter")
@Order(2)
class AuthFilter : Filter {

    @Value("\${app.baseUrl}")
    lateinit var baseUrl : String

    override fun doFilter(req: ServletRequest, resp: ServletResponse, filterChain: FilterChain?) {
        val request = req as HttpServletRequest
        val response = resp as HttpServletResponse
        if (request.cookies == null || request.cookies.none { it.name == "auth" && it.value.toLong() < System.currentTimeMillis() }) {
            if (request.requestURL.contains("/api/")) {
                response.sendError(403, "Login form $baseUrl/app/login")
            } else {
                response.sendRedirect("/login")
            }
        } else {
            filterChain?.doFilter(req, resp)
        }
    }
}