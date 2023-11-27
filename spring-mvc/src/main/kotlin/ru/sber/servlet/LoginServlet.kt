package ru.sber.servlet

import mu.KLogging
import ru.sber.database.DbService
import java.time.LocalDateTime
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Сервлет обрабатывает запросы на url /login
 */
class LoginServlet(private val dbService: DbService) : HttpServlet() {

    companion object : KLogging() {
        const val AUTH_HEADER: String = "auth"
    }

    //После редиректа с любой страница на /login требуется явно указать, что есть get-метод.
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req?.getRequestDispatcher("login.html")?.forward(req, resp)
    }

    /**
     * Запрос аутентификации пользователя
     */
    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("username")
        val pass = req?.getParameter("password")

        if (login != null && pass != null && dbService.getUsers().get(login) == pass) {
            resp?.addCookie(Cookie(AUTH_HEADER, LocalDateTime.now().toString()))
            resp?.sendRedirect("/app/list")
        } else {
            logger.warn { "Не найдена пара логин - пароль. Редирект на страницу аутентификации" }
            resp?.sendRedirect("/login")
        }
    }
}