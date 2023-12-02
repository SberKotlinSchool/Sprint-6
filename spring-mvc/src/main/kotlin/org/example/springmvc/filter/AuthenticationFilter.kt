package org.example.springmvc.filter

import java.time.LocalDateTime
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/rest/*"])
class AuthenticationFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val cookies = request.cookies
        val authenticationSuccess = cookies != null && Arrays.stream(cookies)
            .anyMatch { cookie ->
                "auth" == cookie.name
                        && LocalDateTime.parse(cookie.value).plusMinutes(15).isAfter(LocalDateTime.now())
            }

        if (authenticationSuccess) {
            filterChain.doFilter(request, response)
        } else {
            response.sendRedirect("/login")
        }
    }
}