package com.homework.addressbook.filter;

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
@Order(1)
class LoggerFilter: Filter {

    companion object {
        private val log = LoggerFactory.getLogger(LoggerFilter::class.java)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        log.info("Запрос к ${(request as HttpServletRequest).requestURL}")
        chain?.doFilter(request, response)
    }

}
