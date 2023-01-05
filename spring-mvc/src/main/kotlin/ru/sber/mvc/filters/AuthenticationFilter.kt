package ru.sber.mvc.filters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import ru.sber.mvc.services.UserValidationService
import ru.sber.mvc.servlets.COOKIE_NAME_AUTH
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "AuthenticationFilter",
    description = "Filter to check authentication"
)
@Order(2)
class AuthenticationFilter @Autowired constructor(
    private val userValidationService: UserValidationService
) : HttpFilter() {

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, filterChain: FilterChain?) {
        val authCookie = request?.cookies?.firstOrNull { cookie -> cookie.name == COOKIE_NAME_AUTH }

        if (authCookie != null && userValidationService.validateAuthCookie(authCookie)) {
            filterChain?.doFilter(request, response)
        } else {
            response?.sendRedirect("/login")
        }
    }
}
