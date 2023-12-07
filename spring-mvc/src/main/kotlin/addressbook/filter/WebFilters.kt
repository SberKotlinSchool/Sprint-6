package addressbook.filter

import addressbook.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter("/*")
@Order(2)
class LoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("incoming request from -> ${request.remoteHost}")

        filterChain.doFilter(request, response)

        logger.info("outgoing response to -> ${request.remoteHost}")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LoggingFilter::class.java)
    }
}

@WebFilter(urlPatterns = ["/app/*", "/rest-controller/*"])
@Order(1)
class AuthFilter(private val authService: AuthService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.cookies == null) {
            println("no cookie AuthFilter")
            println(request.method)

            response.sendRedirect("/login")
        } else {
            filterChain.doFilter(request, response)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthFilter::class.java)
    }
}