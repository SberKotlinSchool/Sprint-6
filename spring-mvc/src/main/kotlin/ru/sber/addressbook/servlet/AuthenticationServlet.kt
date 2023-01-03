package ru.sber.addressbook.servlet

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.RequestDispatcher
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/check")
class AuthenticationServlet : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("authenticationform.html").forward(request, response)
    }


    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login");
        val password = req?.getParameter("password")

        if (!password.equals("admin") || !login.equals("admin")) {
            val loginURI: String = req?.contextPath + "/login"
            resp?.sendRedirect(loginURI)
        } else {
            val cookie = Cookie(
                "auth",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss"))
            )
            resp?.addCookie(cookie)
            resp?.sendRedirect("/app/list")
        }
    }
}