package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.model.User
import ru.sber.service.UserService
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/")
class LoginController(@Autowired val userService: UserService) {

    @GetMapping("/login")
    fun loginForm(model: Model, @RequestBody user: User?): String {
        model.addAttribute("user", user)
        return "login"
    }

    @PostMapping("/login")
    fun add(model: Model, @ModelAttribute("user") user: User, response: HttpServletResponse): String {
        return if (userService.isUserCorrect(user)) {
            val cookie = Cookie("auth", "${LocalDateTime.now()}")
            response.addCookie(cookie)
            "redirect:/app/list"
        } else {
            model.addAttribute("user", User("", ""))
            "login"
        }
    }

    @GetMapping("/logout")
    fun logout(model: Model, request: HttpServletRequest): String {
        request.session.invalidate()
        model.addAttribute("user", User("", ""))
        return "login"
    }
}