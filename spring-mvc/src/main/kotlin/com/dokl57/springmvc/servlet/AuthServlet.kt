package com.dokl57.springmvc.servlet

import com.dokl57.springmvc.service.UserAuthenticationService
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@WebServlet(urlPatterns = ["/authenticate"])
class AuthServlet(private val userAuthService: UserAuthenticationService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username = req.getParameter("username")
        val password = req.getParameter("password")

        if (userAuthService.validateUser(username, password)) {
            val loginTime = LocalDateTime.now().plusMinutes(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val authCookie = Cookie("auth", loginTime)
            resp.addCookie(authCookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.sendRedirect("/authenticate?error=true")
        }
    }
}
