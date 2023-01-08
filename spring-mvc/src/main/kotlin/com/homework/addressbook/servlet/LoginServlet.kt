package com.homework.addressbook.servlet

import org.springframework.beans.factory.annotation.Value
import java.io.PrintWriter
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(urlPatterns = ["/login"], name = "loginServlet")
class LoginServlet(@Value("\${auth.login}")
                   private val login: String,
                   @Value("\${auth.password}")
                   private val password: String): HttpServlet() {


    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("login.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

        val login = request.getParameter("login")
        val password = request.getParameter("pwd")

        if (login == this.login || password == this.password) {
            val authCookie = Cookie("auth", LocalDateTime.now().toString())
            authCookie.maxAge = 600 // 10 min
            response.addCookie(authCookie)
            response.sendRedirect("/app/list")
        } else {
            response.contentType = "text/html"
            val printWriter: PrintWriter = response.writer
            printWriter.print("<html>")
            printWriter.print("<body>")
            printWriter.print("<h1>Authorization error</h1>")
            printWriter.print("<a href=\"/\">Try again</a>")
            printWriter.print("</body>")
            printWriter.print("</html>")
            printWriter.close()
        }
    }

}