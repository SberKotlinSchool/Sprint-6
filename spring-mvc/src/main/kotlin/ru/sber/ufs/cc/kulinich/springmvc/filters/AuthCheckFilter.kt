package ru.sber.ufs.cc.kulinich.springmvc.filters

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDate.now
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(urlPatterns = ["/app/*","/api/*"])
@Order(2)
class AuthCheckFilter: Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        if ((request.cookies == null
                    || request.cookies.filter{it.name == "auth"}.isEmpty()
                    || LocalDate.parse(request.cookies.find{it.name == "auth"}!!.value)
                    < now())
            && request.requestURI != "/login") {

            response.sendRedirect("/login")
            return
        }

        p2?.doFilter(p0, p1)
    }
}