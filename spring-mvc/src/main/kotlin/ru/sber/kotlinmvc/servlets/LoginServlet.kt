package ru.sber.kotlinmvc.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.kotlinmvc.services.AuthService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    urlPatterns = ["/login"]
)
class LoginServlet(@Autowired val loginService : AuthService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        if (loginService.authorize(login, password)) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp.sendRedirect("/addressBook/list")
        } else {
            resp.writer.println("Incorrect login or password")
            resp.writer.close()
        }
    }
}