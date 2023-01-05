package ru.sber.mvc.servlets

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod
import ru.sber.mvc.services.UserValidationService
import java.lang.invoke.MethodType
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private const val REQUEST_PARAMETER_USERNAME = "username"
private const val REQUEST_PARAMETER_PASSWORD = "password"

const val COOKIE_NAME_AUTH = "auth"

@WebServlet(
    name = "AuthenticationServlet",
    description = "Servlet for authentication",
    urlPatterns = ["/login"]
)
class AuthenticationServlet @Autowired constructor(
    private val userValidationService: UserValidationService
) : HttpServlet() {

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/login.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        request?.run {
            val username = getParameter(REQUEST_PARAMETER_USERNAME).orEmpty()
            val password = getParameter(REQUEST_PARAMETER_PASSWORD).orEmpty()

            if (userValidationService.validateUser(username, password)) {
                val authCookie = Cookie(COOKIE_NAME_AUTH, LocalDateTime.now().toString())
                response?.addCookie(authCookie)
                response?.sendRedirect("/app/list")
            } else {
                response?.writer?.use { it.write("Improper credentials.") }
            }
        }
    }
}
