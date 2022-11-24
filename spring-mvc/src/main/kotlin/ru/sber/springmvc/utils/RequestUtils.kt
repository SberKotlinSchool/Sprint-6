package ru.sber.springmvc.utils

import mu.KotlinLogging
import ru.sber.springmvc.dto.Note
import ru.sber.springmvc.dto.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie

class RequestUtils {

    companion object {
        const val AUTH = "auth"
        const val USER = "user"
        const val ADMIN = "admin"
        const val DAY_MONTH_HMS = "dd.MM HH:mm:ss"
        val LOG = KotlinLogging.logger {}

        var usersDB = ConcurrentHashMap<String, String>()
        val notes = ArrayList<Note>()

        fun registerUser(user: User) {
            LOG.info { "Adding user ${user.userName} to the local DB" }
            usersDB[user.userName] = user.password
            notes.add(Note(user.userName,"Hello, I'm here!", LocalDateTime.now().format(DateTimeFormatter.ofPattern(DAY_MONTH_HMS))))
        }

        fun clearCookies(cookies: Array<Cookie>?) {
            if (cookies == null) return
            LOG.info { "Clearing cookies..." }
            for (cookie in cookies) {
                cookie.maxAge = 0
                when (cookie.name) {
                    AUTH -> cookie.value = Long.MAX_VALUE.toString()
                    USER -> cookie.value = ""
                }
            }
        }
    }
}