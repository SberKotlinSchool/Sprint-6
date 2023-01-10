package io.vorotov.diary.servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import io.vorotov.diary.models.UserInfo
import io.vorotov.diary.services.UsersService
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