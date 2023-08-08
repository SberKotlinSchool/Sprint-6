package com.example.springmvc.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class AuthFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val req = p0 as HttpServletRequest

        if (authConfirmed(req.cookies))
            p2!!.doFilter(p0, p1)
        else {
            (p1 as HttpServletResponse).sendError(401, "Authorisation fail")
        }
    }

    private fun authConfirmed(cookie: Array<Cookie>?): Boolean {
        return LocalDateTime.now() > LocalDateTime.parse(
            cookie?.find { it.name.equals("auth") }?.value ?: return false
        )
    }
}