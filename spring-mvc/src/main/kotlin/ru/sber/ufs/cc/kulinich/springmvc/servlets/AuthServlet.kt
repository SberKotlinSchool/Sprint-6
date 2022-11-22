package ru.sber.ufs.cc.kulinich.springmvc.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.ufs.cc.kulinich.springmvc.services.AuthService
import java.time.LocalDate.now
import javax.servlet.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    name = "Auth",
    urlPatterns = ["/login"]
)
class AuthServlet(@Autowired val authService: AuthService) : Servlet {

    private lateinit var servletConfig: ServletConfig

     private fun processGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletConfig.servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }


    private fun processPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        if (password == null || login == null)
            servletConfig
                .servletContext
                .getRequestDispatcher("/login")
                .forward(req, resp)

        // Костя, когда будешь провероять, подскажит где это должно быть тут
        // или надо этот кусок кода перенести в сервис?
        if (this.authService.isAuthCorrect(login!!, password!!)) {
            resp?.addCookie(Cookie("auth", now().toString()))
            servletConfig
                .servletContext
                .getRequestDispatcher("/app/list")
                .forward(req, resp)
        }

    }

    override fun init(p0: ServletConfig?) {
        if (p0 != null) {
            servletConfig = p0
        }
    }

    override fun getServletConfig(): ServletConfig {
        return servletConfig
    }

    override fun service(p0: ServletRequest?, p1: ServletResponse?) {
        val request = (p0 as  HttpServletRequest)
        if (request.method == "GET"){
            processGet(request, p1 as HttpServletResponse?)
         }
        if (request.method == "POST"){
            processPost(request, p1 as HttpServletResponse?)
        }
    }

    override fun getServletInfo(): String {
        return "Logging servlet info"
    }

    override fun destroy() {
        //no idea what to do here
    }
}