package ru.sber.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.sber.service.AuthService
import java.time.LocalDateTime

@WebServlet(urlPatterns = ["/login"])
class AuthServlet(private val authService: AuthService) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        val login = request?.getParameter("login")
        val password = request?.getParameter("password")
        if (!login.isNullOrEmpty() && !password.isNullOrEmpty() && authService.isLoginSuccess(login, password)) {
            response?.addCookie(Cookie("login1", LocalDateTime.now().plusMinutes(2).toString()))
            response?.sendRedirect("app/list")
        } else {
            response?.sendRedirect("/login")
        }
    }
}