package ru.sber.addressbook.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.sber.addressbook.service.LogService
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest


@Component
@WebFilter(urlPatterns = ["/*"])
@Order(1)
class LogFilter : Filter {
    @Autowired
    private val logService: LogService? = null

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val servletPath: String = req.servletPath
        logService!!.add("ServletPath :" + servletPath + ", URL =" + req.requestURL)
        chain!!.doFilter(request, response)
    }
}
