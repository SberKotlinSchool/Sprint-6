package ru.sber.addressbook.servlet

import ru.sber.addressbook.model.User
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/login")
class LoginServlet : HttpServlet() {

    val user = User("admin", "1234")

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.getParameter("username")
        val password = req.getParameter("password")
        if (user.login == username && user.password == password) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp.sendRedirect("/persons")
        } else {
            resp.sendRedirect("/login-error")
        }
    }
}