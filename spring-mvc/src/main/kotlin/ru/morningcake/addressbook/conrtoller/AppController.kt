package ru.morningcake.addressbook.conrtoller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import ru.morningcake.addressbook.service.NoteService
import ru.morningcake.addressbook.utils.AppUtils
import java.util.*

@Controller
@RequestMapping("/app")
class AppController @Autowired constructor(private val service: NoteService, private val appUtils : AppUtils) {

    @PostMapping("/list")
    fun fromAuth(model: Model, @CookieValue("userName") userName : String): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        addNotesToModel(model)
        return "book"
    }

    @GetMapping("/list")
    fun showBook(model: Model, @CookieValue("userName") userName : String): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        addNotesToModel(model)
        return "book"
    }

    @PostMapping("/filter")
    fun showFilteredBook(model: Model, @CookieValue("userName") userName : String, filter : String): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        addNotesToModel(model, filter)
        return "book"
    }

    @GetMapping("/create")
    fun createNoteForm(model: Model, @CookieValue("userName") userName : String): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        return "note_create"
    }

    @PostMapping("/create")
    fun createNote(model: Model, @CookieValue("userName") userName : String, dto : NoteDto): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        val created = service.create(dto)
        addNoteToModel(model, created)
        return "note"
    }

    @GetMapping("/note/{id}")
    fun showNote(model: Model, @CookieValue("userName") userName : String, @PathVariable id : UUID): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        val note = service.getById(id)
        addNoteToModel(model, note)
        return "note"
    }

    @PostMapping("/note/{id}/update")
    fun updateNote(model: Model, @CookieValue("userName") userName : String, dto : NoteDto, @PathVariable id : UUID): String {
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        val updated = service.update(id, dto)
        addNoteToModel(model, updated)
        return "note"
    }

    @GetMapping("/note/{id}/delete")
    fun deleteNote(model: Model, @CookieValue("userName") userName : String, @PathVariable id : UUID): String {
        service.delete(id)
        appUtils.addBaseUrlAndUserNameToModel(model, userName)
        addNotesToModel(model)
        return "book"
    }

    private fun addNotesToModel(model: Model) {
        val notes = service.getAll()
        model.addAttribute("notes", notes)
    }

    private fun addNotesToModel(model: Model, filter : String) {
        val filtered = service.search(filter)
        model.addAttribute("notes", filtered)
        model.addAttribute("filter", filter)
    }

    private fun addNoteToModel(model: Model, note : Note) {
        model.addAttribute("note", note)
    }
}