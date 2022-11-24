package ru.sber.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.dto.Note
import ru.sber.springmvc.dto.Note.Companion.deleteNote
import ru.sber.springmvc.dto.Note.Companion.editNote
import ru.sber.springmvc.dto.Note.Companion.saveNote
import ru.sber.springmvc.utils.RequestUtils.Companion.notes

@Controller
@RequestMapping("/message")
class MessageController {

    @PostMapping("/new")
    fun newMessage(@ModelAttribute note: Note): String {
        saveNote(note)
        return "redirect:/app"
    }

    @PostMapping("/delete/{id}")
    fun deleteMessage(@PathVariable("id") id: String): String {
        deleteNote(id.toInt())
        return "redirect:/app"
    }

    @GetMapping("/edit/{id}")
    fun editMessage(@PathVariable("id") id: String, model: Model): String {
        model.addAttribute("id", id)
        model.addAttribute("notes", notes)
        return "message"
    }

    @PostMapping("/edit/{id}")
    fun processEditMessage(@PathVariable("id") id: String, @ModelAttribute note: Note, model: Model): String {
        editNote(id.toInt(), note)
        model.addAttribute("notes", notes)
        return "redirect:/app"
    }

}