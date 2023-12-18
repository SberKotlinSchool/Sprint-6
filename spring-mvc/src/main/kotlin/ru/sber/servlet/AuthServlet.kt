package ru.sber.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.sber.model.User
import ru.sber.service.AuthService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@WebServlet(urlPatterns = ["/login"])
class AuthServlet(private val authService: AuthService) : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        println("AuthServlet")
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = User(req.getParameter("username"), req.getParameter("password"))
        if (authService.authenticate(user)) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)))
            resp.sendRedirect("/api/list")
        } else {
            resp.sendRedirect("/login")
        }
    }
}