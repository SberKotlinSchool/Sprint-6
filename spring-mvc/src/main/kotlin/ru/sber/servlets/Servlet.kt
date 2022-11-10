package ru.sber.servlets

//import ru.company.model.Credential
//import ru.company.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import ru.sber.model.User
import ru.sber.services.UserService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(
    name = "UserServlet",
    urlPatterns = ["/login"]
)
class Servlet(@Autowired val service: UserService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/authorization.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val password = req.getParameter("password")


        if ((login != null && password != null) && service.checkUser(User(login, password))) {
            val cookie = Cookie("auth", LocalDateTime.now().toString());
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.writer.println("Authorization failed")
            resp.writer.close()
        }
    }

}