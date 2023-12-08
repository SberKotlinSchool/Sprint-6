package addressbook.servlet

import addressbook.service.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    urlPatterns = ["/login"]
)
class AuthServlet(private val authService: AuthService) : HttpServlet() {

    private val requestParamUserName = "username"
    private val requestParamPassword = "password"

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        println("AuthServlet")
        request.getRequestDispatcher("/login.html").forward(request, response)
    }


    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val userName = request.getParameter(requestParamUserName)
        val password = request.getParameter(requestParamPassword)

        response.let {
            val isVerifyUser = authService.checkCredentials(userName, password)
            if (isVerifyUser) {
                logger.info("success checkCredentials for user = $userName")
                val loginTime = LocalDateTime.now().plusMinutes(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                val authCookie = Cookie("auth", loginTime)
                response.addCookie(authCookie)

                response.sendRedirect("/app/list")

            } else {
                logger.error("permission denied for user = $userName, redirecting to login form")
                it.sendRedirect("/login")
            }
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthServlet::class.java)
    }
}