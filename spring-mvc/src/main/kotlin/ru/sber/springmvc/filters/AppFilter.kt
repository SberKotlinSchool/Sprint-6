package ru.sber.springmvc.filters

import ru.sber.springmvc.utils.RequestUtils.Companion.AUTH
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app"],
    servletNames = ["SpringMvcApplication"])
class AppFilter : Filter {
    override fun doFilter(p0: ServletRequest, p1: ServletResponse, p2: FilterChain) {
        val rq = p0 as HttpServletRequest
        val rs = p1 as HttpServletResponse

        val cookies = rq.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name.equals(AUTH) && Date(cookie.value!!.toLong()).before(Date())) {
                    rq.session.setAttribute(AUTH, "ok")
                    break
                }
            }
        }
        p2.doFilter(rq, rs)
    }
}
