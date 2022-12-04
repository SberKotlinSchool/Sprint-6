package ru.sbrf.addressbook.web

import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import ru.sbrf.addressbook.core.User
import ru.sbrf.addressbook.core.UserValidationService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    name = "UserValidationServlet",
    urlPatterns = ["/login"]
)
class UserValidationServlet @Autowired constructor(val service: UserValidationService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/authorization.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        val login = req.getParameter("login")
        val password = req.getParameter("password")
        val id: Long = (login.hashCode() + password.hashCode()).toLong();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            resp.writer.println("Authorization failed, please check provided credentials")
            resp.writer.close()
            return
        }

        if (service.checkUserAccess(User(id, login, password))) {
            val cookie = Cookie("auth", LocalDateTime.now().toString());
            resp.addCookie(cookie)
            resp.sendRedirect("/app/list")
        } else {
            resp.writer.println("Authorization failed")
            resp.writer.close()
        }
    }
}
