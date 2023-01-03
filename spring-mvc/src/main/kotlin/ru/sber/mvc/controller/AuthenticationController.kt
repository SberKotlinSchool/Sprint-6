package ru.sber.mvc.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class AuthenticationController {

    @GetMapping
    fun login(model: Model): String {
        return "authenticationForm"
    }
}