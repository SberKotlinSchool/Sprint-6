package com.example.notebook.filters


import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/mainpage"],
    servletNames = ["NotebookApplication"])
class AuthFilter: Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request as HttpServletRequest
        response as HttpServletResponse

        val cookies = request.cookies
        if(cookies != null){
        for (cookie in cookies) {
            if (cookie.name == "auth" && cookie.value.toLong() < System.currentTimeMillis()) {
                chain.doFilter(request, response)
                break
            }
        }
        }

        response.sendRedirect("/login")
    }
}