package ru.morningcake.addressbook.servlet

import org.springframework.beans.factory.annotation.Autowired
import ru.morningcake.addressbook.service.UserService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/login/auth")
class AuthServlet @Autowired constructor (private val service: UserService): HttpServlet() {

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val login = request.getParameter("login")
        val password = request.getParameter("password")

        if (service.checkAuth(login, password)) {
            val user = service.getUser(login)
            val authCookie = Cookie("auth", System.currentTimeMillis().toString())
            authCookie.path = "/"
            response.addCookie(authCookie)
            val nameCookie = Cookie("userName", user!!.name)
            nameCookie.path = "/"
            response.addCookie(nameCookie)
            val dispatcher = request.getRequestDispatcher("/app/list")
            dispatcher.forward(request, response)
        }
        else {
            response.sendRedirect("/login")
        }
    }
}