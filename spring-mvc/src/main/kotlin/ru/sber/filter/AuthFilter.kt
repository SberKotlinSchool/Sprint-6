package ru.sber.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Order(2)
@WebFilter
class AuthFilter: HttpFilter() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        request as HttpServletRequest
        if ( request.requestURI == "/login" ){
            chain!!.doFilter(request, response)
            return }
        val time = request.cookies?.find { it.name == "auth" }?.value
        if (time.isNullOrEmpty())
            ( response as HttpServletResponse).sendRedirect("/login")
        else if ( LocalDateTime.now() < LocalDateTime.parse(time) )
                ( response as HttpServletResponse).sendRedirect("/login")

        chain!!.doFilter(request, response)
    }
}