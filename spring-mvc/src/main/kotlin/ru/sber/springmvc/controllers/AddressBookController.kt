package ru.sber.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record

@Controller
@RequestMapping("/app")
class AddressBookController {

    private final val addressBook: AddressBookRepository

    constructor(addressBook: AddressBookRepository) {
        this.addressBook = addressBook
    }

    //    /login форма входа только публичный доступ, остальные ресурсы будут только аутентифицированный доступ
//    /app/list просмотр записей и поиск если будет переданы query параметры запроса
//    /app/{id}/view просмотр конкретной записи
//    /app/{id}/edit редактирование конкретной записи
//    /app/{id}/delete удаление конкретной записи
//    /api/... с теми же действиями, но только будет применяться json для запроса и ответов.

    @PostMapping("/add")
    fun addRecord(@ModelAttribute("record") record: Record): String {
        addressBook.addRecord(record)
        return "success"
    }

    @GetMapping("/list")
    fun showAll(model: Model): String {
        model.addAttribute("records", addressBook.getAll())
        return "showAll"
    }

    @GetMapping("/{id}/view")
    fun getById(@PathVariable("id") id: Long, model: Model): String? {
        model.addAttribute("recordById", addressBook.getById(id))
        return "getById"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable(name = "id") id: Long) {
        addressBook.deleteById(id)
    }

    @GetMapping("/{id}/edit")
    fun editGet(model: Model, @PathVariable id: Long): String {
        model.addAttribute("record", addressBook.getById(id))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editPost(record: Record, @PathVariable id: String): String {
        addressBook.addRecord(record)
        return "redirect:/app/list"
    }
}