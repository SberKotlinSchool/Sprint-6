package com.sbuniver.homework.servlet

import javax.servlet.RequestDispatcher
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(urlPatterns = ["/login"])
class LoginServlet : HttpServlet() {

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        println("=============LoginServlet ========doGet")
        val view = request?.getRequestDispatcher("login.html")
        view?.forward(request, response)
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        response?.sendRedirect("/app/list")
    }
}