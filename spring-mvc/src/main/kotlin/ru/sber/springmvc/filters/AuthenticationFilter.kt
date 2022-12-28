package ru.sber.springmvc.filters

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/app/*", "/api/*"],
    filterName = "AuthenticationFilter",
    description = "Filter URLs that need authentication"
)
class AuthenticationFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        if (request.cookies == null ||
            request.cookies.none {
                it.name == "auth"
                // && LocalDateTime.parse(it.value) < LocalDateTime.now()
            }
        ) {
            response.sendRedirect("/login")
        } else {
            chain.doFilter(req, res)
        }
    }
}