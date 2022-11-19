package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import ru.sber.springmvc.repository.LoginPass
import ru.sber.springmvc.services.AuthService

@Controller
class SignInController {

    @Autowired
    lateinit var authService: AuthService

    @GetMapping("/signIn")
    fun signIn(): String {
        return "signIn"
    }

    @PostMapping("/signIn")
    fun signInProcessing(@ModelAttribute userData: LoginPass, model: Model): String {
        println(userData)
        userData.localDB["123"] = "123"
        return if (authService.checkUserCred(userData)) "index"
        else
            "register"
    }
}