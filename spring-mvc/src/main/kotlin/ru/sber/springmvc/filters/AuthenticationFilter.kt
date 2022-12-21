package ru.sber.springmvc.filters

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "AuthenticationFilter",
    description = "Filter URLs that need authentication"
)
class AuthenticationFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
        println("AuthenticationFilter is started")
    }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        val requestPath: String = request.getRequestURI()
        println("requestPath from AuthFilter  $requestPath")

        if (request.cookies == null ||
            request.cookies.none {
                it.name == "auth"
                // && LocalDateTime.parse(it.value) < LocalDateTime.now()
            }
        ) {
            println("перенаправление на ${"/login"}")
            response.sendRedirect("/login")
        } else {
            chain.doFilter(req, res)
        }
    }
}