package com.example.notebook.filters.rest

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/api/notes/*"],
    servletNames = ["NotebookApplication"])
class AuthApiFilter : Filter{

    override fun doFilter(rq: ServletRequest, rs: ServletResponse, chain: FilterChain) {
        rq as HttpServletRequest
        rs as HttpServletResponse

        val cookies = rq.cookies
        if(cookies != null){
            for (cookie in cookies) {
                if (cookie.name == "auth" && cookie.value.toLong() < System.currentTimeMillis()) {
                    chain.doFilter(rq, rs)
                    return
                }
            }
        }

        rs.sendError(403)
    }
}