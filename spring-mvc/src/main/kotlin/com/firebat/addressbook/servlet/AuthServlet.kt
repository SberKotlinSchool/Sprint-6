package com.firebat.addressbook.servlet

import com.firebat.addressbook.service.AuthService
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebServlet(urlPatterns = ["/login"])
class AuthServlet(private val authService: AuthService) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        if (!login.isNullOrEmpty() && !password.isNullOrEmpty() && authService.isLoginSuccess(login, password)) {
            resp?.addCookie(Cookie("auth1", LocalDateTime.now().plusMinutes(2).toString()))
            resp?.sendRedirect("/app/list")
        } else {
            resp?.sendRedirect("/login")
        }
    }
}