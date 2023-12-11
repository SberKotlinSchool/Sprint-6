package com.example.adressbook.servlet

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(
    description = "Servlet for redirection to starting page",
    urlPatterns = [""]
)
class IndexServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        resp?.sendRedirect("/app/list")
    }
}