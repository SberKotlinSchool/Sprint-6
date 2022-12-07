package ru.sber.springmvc

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

        if (login == "test" && password == "test") {
            val cookie = Cookie("auth", LocalDateTime.now().toString());

            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else
            resp.sendRedirect("/login")
    }
}
