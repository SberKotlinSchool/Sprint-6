package ru.sber.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RegisterController {

    @GetMapping("/register")
    fun register(): String {
        return "register"
    }
}