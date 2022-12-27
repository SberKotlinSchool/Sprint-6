package ru.sber.mvc.servlets

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.mvc.models.User
import ru.sber.mvc.services.LoginServiceCheckable
import java.time.LocalDateTime

@WebServlet(
    urlPatterns = ["/login"]
)
class LoginServlet  @Autowired constructor(private val service: LoginServiceCheckable) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val password = req.getParameter("password")

        if (!(login.isNullOrEmpty()) && !(password.isNullOrEmpty()) && service.isAccessAllowed(User(login, password)) ) {
            val cookie = Cookie("auth", LocalDateTime.now().toString());
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.writer.println("<p>Credentials are incorrect, please try again...</p>")
            resp.writer.close()
        }
    }
}


