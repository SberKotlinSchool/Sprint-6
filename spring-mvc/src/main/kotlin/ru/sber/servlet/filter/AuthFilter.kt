package ru.sber.servlet.filter


import mu.KLogging
import ru.sber.servlet.LoginServlet
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Фильтр входящий http-запросов для проверки аутентифицированности пользователя
 */
class AuthFilter : HttpFilter() {

    companion object : KLogging()

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val allowed = !request.cookies.isNullOrEmpty()
                && (request.cookies
                .firstOrNull { it.name == LoginServlet.AUTH_HEADER }?.run {
                    LocalDateTime.parse(value).isBefore(LocalDateTime.now())//Время первого входа ранее текущего. Бесконечная сессия.
                } ?: false)

        if (allowed) {
            logger.info { "authentication - ok" }
            filterChain.doFilter(request, response)
        } else {
            logger.info { "authentication - not ok. redirecting" }
            response.sendRedirect("/login")
        }
    }
}