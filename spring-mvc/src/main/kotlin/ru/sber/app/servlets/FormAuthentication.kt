package ru.sber.app.servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.*

@WebServlet(urlPatterns = ["/login"])
class FormAuthentication : HttpServlet() {

    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        req.getRequestDispatcher("/enter.html").forward(req, res)
    }
}