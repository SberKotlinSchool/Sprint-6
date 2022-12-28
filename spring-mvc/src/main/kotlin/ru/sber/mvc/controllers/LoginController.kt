package ru.sber.mvc.controllers

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @GetMapping("/login")
    fun login(model: Model) : String {
        return "login"
    }

}