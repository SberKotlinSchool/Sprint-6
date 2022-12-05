package com.sbuniver.homework.servlet

import mu.KotlinLogging
import java.time.LocalDateTime
import java.time.LocalTime
import javax.servlet.RequestDispatcher
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(urlPatterns = ["/login"])
class LoginServlet : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("login.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

        val email = request.getParameter("email")
        val password = request.getParameter("pwd")

        if (email == PREDEFINED_EMAIL || password == PREDEFINED_PASSWORD) {
            logger.info { "$email authenticated!" }
            response.addCookie(Cookie(CredentialsFilter.AUTH_COOKIE_NAME,LocalTime.now().toString()))
            response.sendRedirect("/app/list")
        } else {
            logger.info { "Authentication failed due to incorrect email or password!" }
            response.sendRedirect("/login")
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
        const val PREDEFINED_EMAIL = "Test@test.ru"
        const val PREDEFINED_PASSWORD = "123456789"
    }
}