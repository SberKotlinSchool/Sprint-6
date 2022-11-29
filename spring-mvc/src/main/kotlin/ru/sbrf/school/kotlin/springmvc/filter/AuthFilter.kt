package ru.sbrf.school.kotlin.springmvc.filter

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*"])
class AuthFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain?) {
        request as HttpServletRequest
        if (isAuthed(request) || isLoginUri(request)) {
            chain!!.doFilter(request, response)
        } else {
            (response as HttpServletResponse).sendRedirect("/login")
        }
    }

    private fun isAuthed(request: HttpServletRequest): Boolean {
        return !request.cookies.isNullOrEmpty() && request.cookies.any {
            it.name == "auth" && LocalDateTime.parse(it.value).plusMinutes(1) > LocalDateTime.now()
        }
    }

    private fun isLoginUri(request: HttpServletRequest) : Boolean {
        return "/login" == request.requestURI.substring(request.contextPath.length).replace("[/]+$", "")
    }
}