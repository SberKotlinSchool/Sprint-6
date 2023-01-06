package ru.sber.app.servlets

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/auth"])
class Authentication(private val trueLogin: String = "2", private val truePassword: String = "3"): Filter {

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {

        val req = req as HttpServletRequest
        val res = res as HttpServletResponse

        var formLogin = req.getParameter("login")
        var formPassword = req.getParameter("password")

        //delete previous cookie with name auth
        var cookie = Cookie("auth","")
        cookie.maxAge = 0
        res.addCookie(cookie)

        if(formLogin == trueLogin && formPassword == truePassword){
            //create new cookie with name auth
            cookie = Cookie("auth",LocalDateTime.now().toString())
            cookie.maxAge = 120 // 2 minutes
            res.addCookie(cookie)
            //delete data about login and password
            formLogin = ""
            formPassword = ""

        } else {
            res.sendRedirect("/warn")
        }

        chain?.doFilter(req,res)
    }
}