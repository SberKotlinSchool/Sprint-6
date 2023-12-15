package com.example.adresbook.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@WebFilter("/*")
class LogFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        p0 as HttpServletRequest?
        p1 as HttpServletResponse?
        LOG.info("Запрос от ${p0?.remoteAddr}")
        p2?.doFilter(p0, p1)
    }

    companion object {
        private val LOG = KotlinLogging.logger {}
    }
}