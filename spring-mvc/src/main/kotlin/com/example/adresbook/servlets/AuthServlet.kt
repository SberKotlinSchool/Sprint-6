package com.example.adresbook.servlets

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime


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
        println("U - $login p - $password")
        if (login == "111" && password == "555") {
            val cookie = Cookie("auth", LocalDateTime.now().toString());
            resp.addCookie(cookie)
            resp.sendRedirect("/app/v1/list")
        } else {
            resp.sendRedirect("/login")
        }
    }
}