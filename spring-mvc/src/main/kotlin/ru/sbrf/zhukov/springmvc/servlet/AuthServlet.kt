package ru.sbrf.zhukov.springmvc.servlet

import ru.sbrf.zhukov.springmvc.service.AuthService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/login"])
class AuthServlet(private val authService: AuthService) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        if (!login.isNullOrEmpty() && !password.isNullOrEmpty() && authService.authenticate(login, password)) {
            resp?.addCookie(Cookie("auth5", LocalDateTime.now().plusMinutes(2).toString()))
            resp?.sendRedirect("/app/list")
        } else {
            resp?.sendRedirect("/login")
        }
    }

}