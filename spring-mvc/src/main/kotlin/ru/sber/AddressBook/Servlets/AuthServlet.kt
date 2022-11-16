package ru.sber.AddressBook.Servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.AddressBook.Services.AuthenticationService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    name = "AuthServlet",
    urlPatterns = ["/login"]
)
class AuthServlet(): HttpServlet() {

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req,resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        val login = req.getParameter("login")
        val password = req.getParameter("password")

        if (login == null || password == null || !authenticationService.checkCredentials(login = login, password = password)) {
            resp.sendRedirect("/login")
            resp.writer.use {
                println("Invalid credentials")
            }
        } else {
            println("Success auth")
            val cookie = Cookie("auth", LocalDateTime.now().plusMinutes(10).toString())
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        }
    }

}