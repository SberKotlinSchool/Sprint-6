package ru.sber.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

@WebServlet(urlPatterns = ["/login"], name = "AuthServlet")
class AuthServlet: HttpServlet() {
    private val pair = Pair("login", "pass")

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req?.getRequestDispatcher("login.html")?.forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login") as String
        val pass = req?.getParameter("password") as String
        if (pair.first == login && pair.second == pass) {
            resp?.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp?.sendRedirect("/app/list")
        } else {
            resp?.sendRedirect("/login")
        }
    }
}