package ru.sber.springmvc.filters

import ru.sber.springmvc.dto.User
import ru.sber.springmvc.utils.RequestUtils.Companion.ADMIN
import ru.sber.springmvc.utils.RequestUtils.Companion.LOG
import ru.sber.springmvc.utils.RequestUtils.Companion.USER
import ru.sber.springmvc.utils.RequestUtils.Companion.clearCookies
import ru.sber.springmvc.utils.RequestUtils.Companion.registerUser
import ru.sber.springmvc.utils.RequestUtils.Companion.usersDB
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"],
    servletNames = ["SpringMvcApplication"])
class LoggerFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val rq = request as HttpServletRequest
        val rs = response as HttpServletResponse

        LOG.info { "Processing ${rq.method} request: ${request.requestURI}" }

        if (usersDB[ADMIN] == null) {
            registerUser(User(ADMIN, ADMIN))
            clearCookies(rq.cookies)
        }
        if (usersDB[USER] == null) {
            registerUser(User(USER, USER))
        }
        chain.doFilter(rq, rs)
    }
}