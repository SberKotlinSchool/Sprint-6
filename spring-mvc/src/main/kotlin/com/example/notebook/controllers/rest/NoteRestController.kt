package com.example.notebook.controllers.rest

import com.example.notebook.model.Note
import com.example.notebook.repository.NotebookRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/notes")
class NoteRestController @Autowired constructor(private val notebookRepository: NotebookRepository) {

    @GetMapping
    fun getNotes(@RequestParam(value = "login") login: String) : List<String>{
        return notebookRepository.getNotesByUser(login)
    }

    @PostMapping("/{login}/delete/{id}")
    fun deleteNote(@PathVariable("id") id: String, @PathVariable("login") login: String) : List<String>{
        return notebookRepository.deleteNote(login, id.toInt())
    }

    @PostMapping("/{login}/new")
    @ResponseBody
    fun addNote(httpEntity: HttpEntity<String>, @PathVariable("login") login: String) : List<String>{
        if (httpEntity.hasBody()) {
            val newNote =
                jacksonObjectMapper().readValue(httpEntity.body, Note::class.java)
            return notebookRepository.addNote(login, newNote.note)
        }
        return notebookRepository.getNotesByUser(login)
    }

}