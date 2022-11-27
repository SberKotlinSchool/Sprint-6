package com.sbuniver.homework.servlet

import com.sbuniver.homework.utils.Constants.Companion.AUTH_COOKIE_NAME
import com.sbuniver.homework.utils.Constants.Companion.PREDEFINED_EMAIL
import com.sbuniver.homework.utils.Constants.Companion.PREDEFINED_PASSWORD
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import java.time.LocalTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter("/login")
@Order(2)
class CredentialsFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        logger.info { "ASD" }
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val authCookie = httpRequest.cookies?.firstOrNull { it.name.equals(AUTH_COOKIE_NAME) }

        if (authCookie != null && LocalTime.now().isAfter(LocalTime.parse(authCookie.value))) {
            request.session.setAttribute("auth", "true")
            chain?.doFilter(request, response)
        } else if (httpRequest.method.equals("POST", ignoreCase = true)) {

            val email = request.getParameter("email")
            val password = request.getParameter("pwd")

            if (email != PREDEFINED_EMAIL || password != PREDEFINED_PASSWORD) {
                logger.info { "Authentication failed due to incorrect email or password!" }
                httpResponse.writer.println("Authentication failed due to incorrect email or password!")
            } else {
                logger.info { "$email authenticated!" }
                request.session.setAttribute("auth", "true")
                chain?.doFilter(request, response)
            }
        } else {
            chain?.doFilter(request, response)
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}