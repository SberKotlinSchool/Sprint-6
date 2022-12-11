package com.example.notebook.servlets.rest

import com.example.notebook.model.User
import com.example.notebook.repository.UserRep
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ResponseBody
import java.util.stream.Collectors
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

@WebServlet("/api/login")
@ResponseBody
class LoginRestServlet @Autowired constructor (private val service: UserRep): HttpServlet(){

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = jacksonObjectMapper().readValue(req.reader.lines().collect(Collectors.joining()), User::class.java)

        if (service.checkUser(user)) {
            resp.addCookie(Cookie("auth", System.currentTimeMillis().toString()))
//            resp.addCookie(Cookie("login", userName))
            resp.status = SC_OK
        }
        else {
            resp.sendError(404)
        }
    }
}