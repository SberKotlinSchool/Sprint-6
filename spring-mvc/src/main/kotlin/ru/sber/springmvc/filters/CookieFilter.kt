package ru.sber.springmvc.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter

@WebFilter(urlPatterns = ["/signIn"],
    servletNames = ["SpringMvcApplication"])
class ExampleFilter : Filter {
    // реализация интерфейса Filter
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        if (p0 != null) {
            println(p0)
        }
        p2!!.doFilter(p0, p1)
    }
}
