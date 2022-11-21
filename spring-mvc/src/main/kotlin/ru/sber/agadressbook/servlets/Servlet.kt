package ru.sber.agadressbook.servlets


import org.springframework.beans.factory.annotation.Autowired
import ru.sber.agadressbook.models.Credentials
import ru.sber.agadressbook.service.AddressBookWebService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(
    name = "LoginServlet",
    urlPatterns = ["/login_form"]
)
class Servlet(@Autowired val addressBookWebService: AddressBookWebService) : HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login_form.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val userLogin = req.getParameter("userName")
        val userPassword = req.getParameter("password")


        if ((userLogin != null && userPassword != null) && addressBookWebService.checkUser(Credentials(userLogin, userPassword))) {
            val cookie = Cookie("auth", LocalDateTime.now().toString())
            resp.addCookie(cookie)
            resp.sendRedirect("/addressbook/list")
        } else {
            resp.sendRedirect("/login_form")
            resp.writer.close()
        }
    }

}