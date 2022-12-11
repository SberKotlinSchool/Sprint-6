package com.example.notebook.controllers

import com.example.notebook.model.Note
import com.example.notebook.model.User
import com.example.notebook.repository.NotebookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/note")
class NoteController @Autowired constructor(private val notebookRepository: NotebookRepository) {

    @PostMapping("/new")
    fun addNewNote(@ModelAttribute note: Note, @ModelAttribute user: User, model: Model): String {
        model.addAttribute("login", user.login)
        model.addAttribute("notes", notebookRepository.addNote(user.login, note.note))
        return "mainpage"
    }

    @PostMapping("/edit")
    fun editNote(@RequestParam("id") id: String, @RequestParam("note") note: String, @ModelAttribute user: User, model: Model): String {
        model.addAttribute("login", user.login)
        model.addAttribute("notes", notebookRepository.editNote(user.login, id.toInt(), note))

        return "mainpage"
    }

    @PostMapping("/delete")
    fun deleteNote(@RequestParam("id") id: String, @ModelAttribute user: User, model: Model): String {
        model.addAttribute("login", user.login)
        model.addAttribute("notes", notebookRepository.deleteNote(user.login, id.toInt()))
        return "mainpage"
    }
}