package ru.sber.login

import ru.sber.AuthCookie
import ru.sber.model.user.UserService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/app/login")
class Login(private val service: UserService) : HttpServlet() {

  override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
    req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res)
  }

  override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
    val username: String = req.getParameter("username")
    val password: String = req.getParameter("password")
    service.getUser(username, password)?.let {
      val authCookie = AuthCookie(it.userId, LocalDateTime.now())
      res.addCookie(Cookie("auth", authCookie.toString()))
      res.sendRedirect("/app/list")
    } ?: req.getRequestDispatcher("/WEB-INF/login.jsp?loginError=true").forward(req, res)
  }
}