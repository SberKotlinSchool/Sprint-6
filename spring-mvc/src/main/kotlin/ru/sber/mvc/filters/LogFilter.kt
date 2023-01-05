package ru.sber.mvc.filters

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(
    urlPatterns = ["/*"],
    filterName = "LogFilter",
    description = "Filter to write logs",
)
@Order(1)
class LogFilter : HttpFilter() {

    private val logger = LoggerFactory.getLogger("LogFilter")

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, filterChain: FilterChain?) {
        request?.run {
            listOf(
                "Request info:",
                "Url: $requestURL",
                "Server: $serverName:$serverPort",
                "Address from: $remoteAddr:$remotePort",
                "Content type: $contentType",
                "Content length: $contentLength",
                "Parameters ${parameterMap.map { it.key to it.value }}",
                "Cookies ${cookies?.map { it.name to it.value }}"
            ).forEach(logger::info)
        }

        filterChain?.doFilter(request, response)
    }
}
