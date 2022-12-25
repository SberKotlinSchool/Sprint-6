package ru.sber.springmvc.servlets

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.sber.springmvc.domain.User
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap


@WebServlet(
    name = "AuthenticationServlet",
    description = "Servlet for  “the hard way”  Authentication",
    urlPatterns = ["/login"]
)
class AuthenticationServlet : HttpServlet() {
    var validUsers = ConcurrentHashMap<Long, User>()

    override fun init() {
        super.init()
        validUsers.put(1, User("Vasya", "123456"))
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        println("зашел в doGet в Servlet ")
        servletConfig.servletContext.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val username: String = req.getParameter("name")
        val password: String = req.getParameter("password")

        validUsers.forEach { t, u ->
            if (u.name.equals(username) && u.password.equals(password)) {
                println("НАШЛАСЬ ПАРА в базе паролей: ${u.name.equals(username) && u.password.equals(password)}")
                resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
                println(LocalDateTime.now().toString())
                println(LocalDateTime.now())
                resp.sendRedirect("/app/list")
            } else {
                resp.writer.use {
                    println("Invalid login or password")
                }
            }
        }
    }
}

//        request.setAttribute("messages", messages)
//        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response)


