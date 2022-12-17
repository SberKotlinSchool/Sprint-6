package ru.sbrf.school.kotlin.springmvc.filter

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

@WebFilter(
    urlPatterns = ["/*"]
)
@Order(2)
class LoggingFilter : Filter {
    private val LOG = KotlinLogging.logger {}
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val wRequest = HttpServletRequestWrapper(request as HttpServletRequest?)
        val wResponse= HttpServletResponseWrapper(response as HttpServletResponse?)

        LOG.info {"request: Address:${wRequest.remoteAddr} Method:${wRequest.method} RequestURI:${wRequest.requestURI}, Status: ${wResponse.status} "}
        chain!!.doFilter(request,response)
    }

}


