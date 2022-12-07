package ru.sber.springmvc.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import ru.sber.springmvc.Config
import java.time.LocalDateTime

class AccessFilter: Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val accessGranted = (request as HttpServletRequest).cookies
            ?.firstOrNull { it.name == "auth" }
            ?.value
            ?.run {
                LocalDateTime.parse(this)
            }
            ?.plusDays(1)
            ?.isAfter(LocalDateTime.now())
            ?: false

        if (!accessGranted) {
            (response as HttpServletResponse).sendRedirect("/login")
        } else
            chain?.doFilter(request, response)
    }
}
