package ru.sber.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap


@WebServlet(urlPatterns=["/login"])
class AddressServlet: HttpServlet() {

    val admin = ConcurrentHashMap<String, String>(mapOf("admin" to "1234"))

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse?) {
        req.getRequestDispatcher("/login.html").forward( req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        val login = req.getParameter("login" ) as String
        val password = req.getParameter("password") as String

        if ( password == admin[login] ){
            resp.addCookie( Cookie("auth", LocalDateTime.now().toString() ) )
            resp.sendRedirect("/app/list")
        } else{
            resp.sendRedirect("/login")
        }

    }
}