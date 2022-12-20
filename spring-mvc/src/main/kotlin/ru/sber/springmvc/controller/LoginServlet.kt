package ru.sber.springmvc.controller

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.ResourceUtils
import java.time.LocalDateTime

@WebServlet(urlPatterns = ["/login"], loadOnStartup = 1)
class LoginServlet(
    @Value("\${auth.login}")
    private val login: String,
    @Value("\${auth.password}")
    private val password: String,
) : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        loadPage("login.html", resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val password = req.getParameter("password")
        if (login == this.login && password == this.password) {
            val authCookie = Cookie("auth", LocalDateTime.now().toString())
            resp.addCookie(authCookie)
            val redirectUrl = req.cookies
                ?.firstOrNull { it.name == "redirect" && it.value.isNotEmpty() }
            resp.addCookie(Cookie("redirect", null))
            resp.sendRedirect(redirectUrl?.value ?: "/app/list")
        } else {
            loadPage("loginWithError.html", resp)
        }
    }

    private fun loadPage(templateName: String, resp: HttpServletResponse) {
        val resource = ResourceUtils.getFile("classpath:templates/$templateName")
        val reader = resource.bufferedReader()
        resp.contentType = "text/html;charset=UTF-8"
        val writer = resp.writer
        var line = reader.readLine()
        while (line != null) {
            writer.println(line)
            line = reader.readLine()
        }
        writer.close()
        reader.close()
    }
}
