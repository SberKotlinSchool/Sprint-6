package ru.sber.adressbook.filters

import org.springframework.core.annotation.Order
import java.time.Instant
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
@WebFilter(urlPatterns = ["/*"])
class LogFilter: HttpFilter() {

    override fun doFilter(rq: HttpServletRequest, rs: HttpServletResponse, chain: FilterChain) {
        LOG.info("${Instant.now()} ${rq.method} ${rq.protocol} ${rq.serverPort} ${rq.requestURL}")
        super.doFilter(rq, rs, chain)
    }

    companion object {
        val LOG: Logger = Logger.getLogger(LogFilter::class.java.name)
    }

}