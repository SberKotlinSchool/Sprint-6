package sber.mvc.application.servlet

import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import sber.mvc.application.service.UserService
import java.time.LocalDateTime

@WebServlet(
    name = "LoginServlet",
    urlPatterns = ["", "/login"]
)
class LoginServlet(@Autowired val service: UserService) : HttpServlet() {

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val login = request.getParameter("login")
        val password = request.getParameter("password")
        if (service.checkCredentials(login, password)) {
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
