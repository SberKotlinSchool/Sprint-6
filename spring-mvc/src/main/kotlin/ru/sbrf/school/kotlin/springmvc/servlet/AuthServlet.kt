package ru.sbrf.school.kotlin.springmvc.servlet

import ru.sbrf.school.kotlin.springmvc.service.AuthService
import ru.sbrf.school.kotlin.springmvc.entity.User
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    urlPatterns = ["/login"]
)
class AuthServlet(private val authService: AuthService) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse) {
        val username = req?.getParameter("username")
        val password = req?.getParameter("password")
        if (!username.isNullOrEmpty() && !password.isNullOrEmpty() && authService.isPermitted(User(username,password))) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp.sendRedirect("/app/list")
        } else {
            resp.writer.println("<div>Invalid username and password</div>")
            resp.writer.close()
        }
    }
}