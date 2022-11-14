package ru.sberbank.school.mvchomework.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AppController {

    @GetMapping("/")
    fun mainPage(): String{
        return "index"
    }
}