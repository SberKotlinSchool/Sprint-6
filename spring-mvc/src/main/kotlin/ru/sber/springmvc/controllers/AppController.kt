package ru.sber.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.springmvc.utils.RequestUtils.Companion.notes

@Controller
@RequestMapping("/app")
class AppController {

    @GetMapping
    fun app(modelMap: ModelMap): String {
        modelMap.addAttribute("notes", notes)
        return "app"
    }

    @PostMapping
    fun processPost(modelMap: ModelMap): String {
        modelMap.addAttribute("notes", notes)
        return "app"
    }
}