package com.example.demo.servlet

import com.example.demo.UserConfigurationProperties
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.ZoneId
import java.time.ZonedDateTime

@WebServlet(urlPatterns = ["/login"], loadOnStartup = 1)
class LoginServlet(
    private val properties: UserConfigurationProperties
) : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher("/login.html")
            .forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.getParameter("uname")
        val password = req.getParameter("psw")
        if (username == properties.username && password == properties.password) {
            val cookie = Cookie("auth", ZonedDateTime.now(ZoneId.of("UTC")).toString())
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.sendRedirect("/error_login.html")
        }
    }
}