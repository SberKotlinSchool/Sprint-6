package ru.sber.spring.mvc.servlet

import ru.sber.spring.mvc.auth.AuthService
import java.time.LocalDateTime
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthServlet(private val authService: AuthService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        if (authService.authentication(req.getParameter("login"), req.getParameter("password"))) {
            with(resp) {
                val cookie = Cookie("auth", LocalDateTime.now().plusMinutes(5).toString())
                cookie.maxAge = 10
                addCookie(cookie)
                sendRedirect("/book")
            }
        } else {
            with(resp.writer) {
                println("Access denied")
                close()
            }
        }
    }
}