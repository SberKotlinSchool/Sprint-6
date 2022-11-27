package ru.sberbank.school.mvchomework.utils

import com.sbuniver.homework.utils.Constants
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Verificator {
    companion object {
        @JvmStatic
        fun verifyCookie(request: HttpServletRequest, response: HttpServletResponse, newPage: String) {
            val rd = when (request.session.getAttribute(Constants.AUTH_COOKIE_NAME)) {
                "true" -> {
                    println("auth is true")
                    request.getRequestDispatcher(newPage)
                }
                else -> {
                    println("auth is NOT true")
                    request.getRequestDispatcher("index.html")
                }
            }
            rd.forward(request, response)
        }
    }
}
