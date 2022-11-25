package ru.sber.springmvc.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.springmvc.dto.User
import ru.sber.springmvc.services.AuthService
import ru.sber.springmvc.utils.RequestUtils.Companion.AUTH
import ru.sber.springmvc.utils.RequestUtils.Companion.USER
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/toSignIn")
class AuthServlet @Autowired constructor (private val service: AuthService): HttpServlet(){

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val dispatcher = req.getRequestDispatcher("/signIn")
        dispatcher.forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val userName = req.getParameter("userName")
        val password = req.getParameter("password")

        if (service.checkUserPass(User(userName, password))) {
            resp.addCookie(Cookie(AUTH, System.currentTimeMillis().toString()))
            req.session.setAttribute(USER, userName)
            resp.sendRedirect("/app")
            return
        }
        val dispatcher = req.getRequestDispatcher("/signIn")
        dispatcher.forward(req, resp)
    }
}