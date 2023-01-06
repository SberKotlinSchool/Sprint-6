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

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class Identification : Filter {
    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {

        val req = req as HttpServletRequest
        val res = res as HttpServletResponse

        if (req.cookies.filter { it.name == "auth" }.map {cookie ->
            if (LocalDateTime.parse(cookie.value) < LocalDateTime.now()){

            } else{
                //delete previous cookie with name auth
                val cookie = Cookie("auth","")
                cookie.maxAge = 0
                res.addCookie(cookie)

                res.sendRedirect("/warn")
            }
        }.isEmpty()) res.sendRedirect("/warn")

        chain?.doFilter(req,res)
    }
}