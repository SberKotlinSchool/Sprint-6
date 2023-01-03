package ru.sber.mvc.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.sber.mvc.service.LogService


@Component
@WebFilter(urlPatterns = ["/*"], filterName = "LogFilter")
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
