package ru.sber.addressbook.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/login-error")
class LoginServletError : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/errorLoginServlet.html").forward(req, resp)
    }
}