package ru.sber.springmvc.auth

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import ru.sber.springmvc.domain.User
import ru.sber.springmvc.service.UserService

@WebServlet("/login")
class AuthServlet(private val userService: UserService): HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login").toString()
        val password = req?.getParameter("password").toString()

        userService.runCatching {
            authenticate(User(login, password))
        }.onSuccess {
            resp?.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp?.sendRedirect("app/list")
        }.onFailure {
            resp?.sendRedirect("/login.html")
        }
    }
}