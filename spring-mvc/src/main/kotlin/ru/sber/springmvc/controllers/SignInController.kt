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

@Controller
@RequestMapping("/signIn")
class SignInController @Autowired constructor(val service: AuthService){

    @GetMapping
    fun signIn(): String {
        return "signIn"
    }

    @PostMapping
    fun signInProcessing(@ModelAttribute user: User, model: ModelMap): String {
        model.addAttribute("error", "Try again")
        return "signIn"
    }
}