package com.example.adressbook.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KLogging
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.minutes

@WebServlet(
    urlPatterns = ["/login"]
)
class LoginServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val password = req.getParameter("password")
        logger.debug("Login = $login, password = $password")
        if (login == "admin" && password == "admin") {
            val cookie = Cookie("auth", LocalDateTime.now().toString()).apply {
                maxAge = COOKIE_LIFE_TIME_IN_MINUTES.minutes.inWholeSeconds.toInt()
            }
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.sendRedirect("/login")
        }
    }

    companion object: KLogging() {
        private const val COOKIE_LIFE_TIME_IN_MINUTES = 5L
    }
}