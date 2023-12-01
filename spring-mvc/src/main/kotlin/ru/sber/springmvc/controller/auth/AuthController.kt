package ru.sber.springmvc.controller.auth

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.springmvc.exception.AuthException
import ru.sber.springmvc.model.User
import ru.sber.springmvc.service.UserService
import java.time.LocalDateTime

@WebServlet("/login")
class AuthController @Autowired constructor (private val userService: UserService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/auth.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        if (checkCredentials(req)) {
            val cookie = Cookie("auth", LocalDateTime.now().toString());
            resp.addCookie(cookie)
            resp.sendRedirect("app/list")
        } else {
            resp.sendRedirect("/login")
        }
    }

    private fun checkCredentials(req: HttpServletRequest): Boolean {
        val login = req.getParameter("login")
        val password = req.getParameter("password")
        if (login == null || password == null) {
            return false
        }
        try {
            userService.authenticate(User(login, password))
        } catch (e: AuthException) {
            return false
        }
        return true
    }
}