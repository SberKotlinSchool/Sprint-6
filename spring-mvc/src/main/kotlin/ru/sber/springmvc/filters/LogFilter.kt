package ru.sber.springmvc.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

class LogFilter: Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        println(
            "Logging Request:\nMethod: ${req.method}\nURI: ${req.requestURI}"
        )

        chain?.doFilter(request, response)

        println(
            "Response: ${res.contentType}"
        )

        println()
    }
}