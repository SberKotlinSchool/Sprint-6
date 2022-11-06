package ru.company.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.company.model.Credential
import ru.company.service.LoginService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(
    name = "LoginServlet",
    urlPatterns = ["/login"]
)
class LoginServlet(@Autowired val service: LoginService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val password = req.getParameter("password")


        if ((login != null && password != null) && service.authentication(Credential(login, password))) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp.sendRedirect("/app/list")
        } else {
            resp.writer.println("Incorrect login or password")
            resp.writer.close()
        }
    }

}