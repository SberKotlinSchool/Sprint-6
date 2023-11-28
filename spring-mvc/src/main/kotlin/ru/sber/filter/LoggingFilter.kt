package ru.sber.filter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter
class LoggingFilter : Filter {

    private val pattern = DateTimeFormatter.ofPattern("YYYY-mm-dd hh:MM:ss.SSS")

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        p0?.let {
            val request = it as HttpServletRequest
            println("${LocalDateTime.now().format(pattern)} Request URL: (${request.requestURL}) " +
                    "method: (${request.method}) " +
                    "cookies: (${
                        request.cookies?.filterNotNull()
                                ?.map { cookie -> "${cookie.name}=${cookie.value}" }
                    }) " +
                    "headers: (${
                        request.headerNames?.toList()
                                ?.map { header -> "${header}=${request.getHeader(header)}" }
                    })")
        }
        p1?.let {
            val response = it as HttpServletResponse
            println("${LocalDateTime.now().format(pattern)} Response " +
                    "headers: (${
                        response.headerNames?.toList()
                                ?.map { header -> "${header}=${response.getHeader(header)}" }
                    }) " +
                    "status: (${response.status})")
        }
        p2?.doFilter(p0, p1)
    }
}