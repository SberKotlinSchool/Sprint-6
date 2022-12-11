package com.example.notebook.controllers

import com.example.notebook.model.User
import com.example.notebook.repository.NotebookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/mainpage")
class MainPageController @Autowired constructor(private val notebookRepository: NotebookRepository){

    @PostMapping
    fun toMainPage(@ModelAttribute user: User, model : Model) : String {
        model.addAttribute("login", user.login)
        model.addAttribute("notes", notebookRepository.getNotesByUser(user.login))
        return "mainpage"
    }

    @GetMapping
    fun toMainPage() : String {
        return "mainpage"
    }
}