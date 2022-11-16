package ru.sber.AddressBook.Filters

import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(urlPatterns = ["/*"], filterName = "LogFilter")
class LogFilter: Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        if (p0 is HttpServletRequest) {
            println("\n__________REQUEST__________")
            println(LocalDateTime.now())
            println("${p0.method} ${p0.requestURL} ")
        }

        if (p1 is HttpServletResponse) {
            println("__________RESPONSE__________")
            println(LocalDateTime.now())
            println("RESPONSE STATUS - ${p1.status}")
        }

        p2?.doFilter(p0, p1)
    }
}