package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.springmvc.dto.User
import ru.sber.springmvc.services.AuthService
import ru.sber.springmvc.utils.RequestUtils.Companion.registerUser

@Controller
@RequestMapping("/register")
class RegisterController @Autowired constructor(val service: AuthService){

    @GetMapping
    fun register(): String {
        return "register"
    }

    @PostMapping
    fun processRegister(@ModelAttribute user: User, model: ModelMap): String {
        if (service.userExists(user)) {
            model.addAttribute("error", "User ${user.userName} already registered")
            return "register"
        } else if (user.userName.length < 3) {
            model.addAttribute("error", "Username length should be more than 2 symbols")
            return "register"
        } else if (user.password.length < 4) {
            model.addAttribute("error", "Password length should be more than 3 symbols")
            return "register"
        }
        registerUser(user)
        model.addAttribute("error", "Now enter your new credentials")
        return "signIn"
    }
}