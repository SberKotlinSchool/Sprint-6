package ru.sber.app.servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/warn")
class WarnWrongPairLoginToPassword: HttpServlet() {

    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        req.getRequestDispatcher("/warn.html").forward(req, res)
    }
}