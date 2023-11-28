package ru.sber.servlet

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ResourceUtils
import ru.sber.model.User
import ru.sber.service.UserService
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/login"])
class LoginServlet(
    @Autowired private val userService: UserService,
) : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        log.info("doGet")
        ResourceUtils.getFile("classpath:static/login.html")
            .bufferedReader()
            .use { reader ->
                response.contentType = "text/html;charset=UTF-8"
                response.writer.use { writer ->
                    var line = reader.readLine()
                    while (line != null) {
                        writer.println(line)
                        line = reader.readLine()
                    }
                }
            }
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        log.info("doPost")
        val user = User(
            request?.getParameter("login") ?: "",
            request?.getParameter("password") ?: ""
        )
        log.info("$user")

        if (userService.isUserCorrect(user)) {
            val cookie = Cookie("auth", "${LocalDateTime.now()}")
            response?.addCookie(cookie)
            response?.sendRedirect("/app/list")
        } else {
            response?.sendRedirect("/login")
        }
    }

    companion object {
        val log = KotlinLogging.logger {}
    }
}