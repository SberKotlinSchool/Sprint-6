package ru.sber.adressbook.servlets

import java.io.File
import java.io.OutputStream
import java.time.Instant
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/login")
class SecurityServlet: HttpServlet() {

    private val username = "admin"
    private val password = "pass"

    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        response!!.contentType = "text/html"
        val text = this::class.java.classLoader.getResource("templates/login.html").readText()

        val outStream: OutputStream = response.outputStream
        outStream.write(text.toByteArray(charset("UTF-8")))
        outStream.flush()
        outStream.close()
    }


    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val usernameForm = request.getParameter("username")
        val passwordForm = request.getParameter("password")
        var redirect = "/login"
        if (username == usernameForm && password == passwordForm) {
            redirect = "/app"
            val cookie = Cookie("auth", Instant.now().epochSecond.toString())
            cookie.maxAge = -1
            response.addCookie(cookie)

            val text = this::class.java.classLoader.getResource("templates/home.html").readText()

            val outStream: OutputStream = response.outputStream
            outStream.write(text.toByteArray(charset("UTF-8")))
            outStream.flush()
            outStream.close()
            return
        }

        response.sendRedirect(request.getContextPath() + redirect);
    }

}