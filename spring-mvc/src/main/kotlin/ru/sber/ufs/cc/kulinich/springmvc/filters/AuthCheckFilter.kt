package ru.sber.ufs.cc.kulinich.springmvc.filters

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter

@Component
@WebFilter(urlPatterns = ["/*"],
    servletNames = ["AddresBookServlet"])
@Order(1)
class AuthCheckFilter: Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        p2?.doFilter(p0, p1)
    //        TODO("Not yet implemented")
    }
}