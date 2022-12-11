package com.example.notebook.servlets

import com.example.notebook.model.User
import com.example.notebook.repository.UserRep
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/checkUser")
class LoginServlet @Autowired constructor (private val service: UserRep): HttpServlet(){

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val userName = req.getParameter("login")
        val password = req.getParameter("pass")

        if (service.checkUser(User(userName, password))) {
            resp.addCookie(Cookie("auth", System.currentTimeMillis().toString()))
            resp.addCookie(Cookie("login", userName))
            val dispatcher = req.getRequestDispatcher("/mainpage")
            dispatcher.forward(req,resp)
        }
        else {
            resp.sendRedirect("/login")
        }
    }
}