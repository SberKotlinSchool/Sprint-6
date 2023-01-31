package ru.sber.servlet

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/login"])
class AuthenticationServlet : HttpServlet() {
    private val users = ConcurrentHashMap<String, User>().apply {
        put("admin", User("admin", "1234"))
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse?) {
        req.getRequestDispatcher("/index.html")?.forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login") as String
        val user = users[login]
        if (user != null && user.password == req.getParameter("password")) {
            resp.addCookie(Cookie("auth", LocalDateTime.now().toString()))
            resp.sendRedirect("/app/list")
        } else {
            resp.sendRedirect("/authError.html")
        }
    }
}

data class User(val login: String, val password: String)