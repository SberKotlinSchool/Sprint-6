package com.example.springmvc.filter

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.concurrent.CopyOnWriteArrayList
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/*"])
class LogFilter : Filter {

    private val log = CopyOnWriteArrayList<String>()
    private val blueColor = "\u001B[34m"

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val req = p0 as HttpServletRequest
        val requestInfo = "SessionID: ${req.session.id} Request URL: ${req.requestURL}, Method: ${req.method}"
        log.add(requestInfo)

        println("${blueColor}${requestInfo}${blueColor}")
        println()

        p2!!.doFilter(p0, p1)

        val resp = p1 as HttpServletResponse
        val session = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session
        val responseInfo = "SessionID: ${session.id}, Response Status: ${resp.status}"
        log.add(responseInfo)

        println("${blueColor}${responseInfo}${blueColor}")
        println()
    }
}