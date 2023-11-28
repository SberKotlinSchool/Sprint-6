package ru.sber.app

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.AuthCookie
import ru.sber.model.record.RecordDAO
import ru.sber.model.record.RecordDTO
import javax.servlet.http.Cookie

@Controller
@RequestMapping("/app")
class MvcController(val repository: RecordDAO) {

  @GetMapping("/list")
  fun getList(model: Model, @CookieValue("auth") cookie: Cookie): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    model.addAttribute("records", repository.getAll(authCookie.userId))
    return "main"
  }

  @GetMapping("/search")
  fun getSearchList(@RequestParam query: String, model: Model, @CookieValue("auth") cookie: Cookie): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    model.addAttribute("records", repository.search(query, authCookie.userId))
    return "main"
  }

  @GetMapping("/add")
  fun showAddForm(model: Model, @CookieValue("auth") cookie: Cookie): String {
    val record = RecordDTO()
    model.addAttribute("record", record)
    return "add"
  }

  @PostMapping("/add")
  fun addRecord(@ModelAttribute recordDTO: RecordDTO, @CookieValue("auth") cookie: Cookie, model: Model): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    repository.insert(recordDTO, authCookie.userId)
    return "redirect:/app/list"
  }

  @GetMapping("{id}/view")
  fun viewRecord(model: Model, @CookieValue("auth") cookie: Cookie, @PathVariable id: Int): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    model.addAttribute("record", repository.get(id, authCookie.userId))
    return "view"
  }

  @GetMapping("/{id}/edit")
  fun showEditForm(model: Model, @CookieValue("auth") cookie: Cookie, @PathVariable id: Int): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    model.addAttribute("record", repository.get(id, authCookie.userId))
    return "edit"
  }

  @PostMapping("/{id}/edit")
  fun editRecord(@ModelAttribute recordDTO: RecordDTO, @CookieValue("auth") cookie: Cookie, model: Model): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    repository.update(recordDTO, authCookie.userId)
    return "redirect:/app/${recordDTO.id}/view"
  }

  @GetMapping("/{id}/delete")
  fun showDeleteForm(model: Model, @CookieValue("auth") cookie: Cookie, @PathVariable id: Int): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    model.addAttribute("record", repository.get(id, authCookie.userId))
    return "delete"
  }

  @PostMapping("/{id}/delete")
  fun deleteRecord(model: Model, @CookieValue("auth") cookie: Cookie, @PathVariable id: Int): String {
    val authCookie = AuthCookie.fromString(cookie.value)
    repository.delete(id, authCookie.userId)
    return "redirect:/app/list"
  }
}