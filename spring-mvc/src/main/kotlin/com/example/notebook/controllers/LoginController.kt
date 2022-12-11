package com.example.notebook.controllers

import com.example.notebook.model.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class LoginController {

    @GetMapping
    fun login(): String {
        return ("login")
    }

    @PostMapping
    fun loginProccessing(@ModelAttribute user: User, model : Model) : String {
        model.addAttribute("login", user.login)
        return "mainpage"
    }
}