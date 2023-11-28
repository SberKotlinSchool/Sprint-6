package ru.sber

import java.time.Duration
import java.time.LocalDateTime

class AuthCookie(val userId: String, val datetime: LocalDateTime) {

  companion object {
    private val pattern = "^(userID=.+)&(datetime=.+)$".toRegex()

    fun fromString(cookie: String): AuthCookie {
      val (userId, datetime) = pattern.find(cookie)!!.destructured
      return AuthCookie(userId.split("=")[1], LocalDateTime.parse(datetime.split("=")[1]))
    }
  }

  override fun toString() = "userID=${userId}&datetime=${datetime}"

  fun validate(): Boolean =
    userId.isNotBlank() && userId.isNotEmpty() && Duration.between(datetime, LocalDateTime.now()).toMinutes() <= 5

}