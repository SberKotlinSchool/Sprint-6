package ru.sber.springmvc.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.slf4j.Logger
import ru.sber.springmvc.model.User
import ru.sber.springmvc.service.UserService
import java.time.LocalDateTime

@WebServlet(urlPatterns = ["/login"])
class AuthenticationServlet(private val service: UserService, private val logger: Logger) : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        servletContext
            .getRequestDispatcher("/authentication.html")
            .forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val user = User(request.getParameter("login"), request.getParameter("password"))
        if (service.check(user)) {
            val cookie = Cookie("authentication", LocalDateTime.now().toString())
            response.addCookie(cookie)
            response.sendRedirect("/app/list")
            logger.info("Success authentication. User: ${user.login}")
        } else {
            response.sendRedirect("/login")
            logger.info("Failed authentication: Permission denied. User: ${user.login}")
        }
    }
}