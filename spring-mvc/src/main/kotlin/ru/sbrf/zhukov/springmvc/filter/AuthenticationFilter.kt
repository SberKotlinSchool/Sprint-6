package ru.sbrf.zhukov.springmvc.filter

import org.springframework.web.filter.OncePerRequestFilter
import ru.sbrf.zhukov.springmvc.service.AuthService
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter("/app/*")
class AuthenticationFilter(
    private val authService: AuthService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authCookie = request.cookies?.find { it.name == "auth" }
        if (authCookie == null || !authService.isAuthenticated(authCookie.value)) {
            response.sendRedirect("/login")
        } else {
            filterChain.doFilter(request, response)
        }
    }
}
