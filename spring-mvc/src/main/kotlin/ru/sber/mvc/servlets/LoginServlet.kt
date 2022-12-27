package ru.sber.mvc.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.mvc.models.User
import ru.sber.mvc.services.LoginServiceCheckable
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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


