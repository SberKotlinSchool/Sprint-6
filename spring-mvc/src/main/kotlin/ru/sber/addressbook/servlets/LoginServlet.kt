package ru.sber.addressbook.servlets

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.addressbook.models.UserInfo
import ru.sber.addressbook.services.UsersService
import java.time.LocalDateTime

@WebServlet(
    name="LoginServ",
    urlPatterns = ["","/login"])
class LoginServlet (@Autowired val service: UsersService): HttpServlet() {

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val login = request.getParameter("login")
        val password = request.getParameter("password")
        if (service.check_users(UserInfo(login, password))) {
            val cookie = Cookie("auth", LocalDateTime.now().toString())
            response.addCookie(cookie)
            response.sendRedirect("/app/list")
        } else {
            response.sendRedirect("/login")
            response.writer.use { println("Failed, permission denied") }
            response.writer.close()
        }
    }
}