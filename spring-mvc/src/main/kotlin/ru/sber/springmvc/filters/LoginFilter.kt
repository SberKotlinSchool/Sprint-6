package ru.sber.springmvc.filters

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/*"],
    filterName = "LoginFilter",
    description = "Filter to check cookie is present"
)
class LoginFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        println(
            "Request received: method ${req.method},   requestURI ${req.requestURI}, has auth cookie:  ${
                req.cookies?.any { it.name == "auth" }
            } "
        )
        chain.doFilter(request, response)
    }
}


