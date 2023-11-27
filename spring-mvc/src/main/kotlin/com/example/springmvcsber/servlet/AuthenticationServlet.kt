package com.example.springmvcsber.servlet

import com.example.springmvcsber.entity.User
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.Logger
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet

@WebServlet(urlPatterns = ["/login"])
class AuthenticationServlet(private val logger: Logger) : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        servletContext
            .getRequestDispatcher("/login.html")
            .forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val user = User(request.getParameter("login"), request.getParameter("password"))

        val cookie = Cookie("authentication", LocalDateTime.now().toString())
        response.addCookie(cookie)
        response.sendRedirect("/app/list")
        logger.info("Success authentication. User: ${user.login}")
    }
}