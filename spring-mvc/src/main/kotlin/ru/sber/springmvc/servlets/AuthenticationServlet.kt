package ru.sber.springmvc.servlets

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.sber.springmvc.User
import ru.sber.springmvc.domain.Record
import java.util.*
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

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val username: String = req.getParameter("name")
        println("username = $username")
        val password: String = req.getParameter("password")
        println("password = $password")

        val user = User(username, password)


        resp.writer.write("Hello, ServletWorld! $username  $password")
        val messages: MutableMap<String, String> = HashMap()

        if (username == null || username.isEmpty()) {
            messages["username"] = "Please enter username"
        }

        if (password == null || password.isEmpty()) {
            messages["password"] = "Please enter password"
        }

        if (messages.isEmpty()) {
            validUsers.forEach { t, u ->
                if (u.name.equals(username) && u.password.equals(password))
                    println("НАШЛАСЬ ПАРА в базе паролей: ${u.name.equals(username) && u.password.equals(password)}")
                req.getSession().setAttribute("user", user)
                resp.sendRedirect(req.getContextPath() + "/app/list")
            }
        } else {
            println("Unknown login, please try again")
            messages["login"] = "Unknown login, please try again"
        }
    }

//        request.setAttribute("messages", messages)
//        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response)
}

