package ru.sber.springmvc.filters

import ru.sber.springmvc.utils.RequestUtils.Companion.AUTH
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/message/*"],
    servletNames = ["SpringMvcApplication"])
class MessageFilter: Filter {
    override fun doFilter(p0: ServletRequest, p1: ServletResponse, p2: FilterChain) {
        val rq = p0 as HttpServletRequest
        val rs = p1 as HttpServletResponse

        if (rq.session.getAttribute(AUTH) == null || rq.session.getAttribute(AUTH) != "ok") {
            val dispatcher = rq.getRequestDispatcher("/app")
            dispatcher.forward(rq, rs)
            return
        }
        p2.doFilter(rq, rs)
    }
}