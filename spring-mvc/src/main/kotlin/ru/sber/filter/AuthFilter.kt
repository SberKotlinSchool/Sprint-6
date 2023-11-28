package ru.sber.filter

import org.springframework.stereotype.Component
import ru.sber.AuthCookie
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@WebFilter(urlPatterns = ["/*"])
class AuthFilter : Filter {

  companion object {
    private val ALLOWED_PATHS: Set<String> = setOf("", "/app/login", "/login", "/")
  }

  override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
    val request = p0 as HttpServletRequest
    val response = p1 as HttpServletResponse
    val path = request.requestURI.substring(request.contextPath.length).replace("/+$".toRegex(), "")

    val cookie = request.cookies?.firstOrNull { it.name == "auth" }
    val auth = cookie != null && AuthCookie.fromString(cookie.value).validate()
    val allowedPath = ALLOWED_PATHS.contains(path)
    if (auth)
      response.addCookie(cookie)
    if (auth || allowedPath)
      p2?.doFilter(request, response)
    else p0.getRequestDispatcher("/WEB-INF/login.jsp").forward(p0, p1)

  }

}