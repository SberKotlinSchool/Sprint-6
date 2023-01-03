package ru.sber.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@WebServlet(urlPatterns = ["/login"])
class AuthenticationServlet : HttpServlet() {

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login");
        val password = req?.getParameter("password")

        if (!password.equals("admin") || !login.equals("admin")) {
            val loginURI: String = req?.contextPath + "/login"
            resp?.sendRedirect(loginURI)
        } else {
            val cookie = Cookie(
                "auth",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
            resp?.addCookie(cookie)
        }
    }
}