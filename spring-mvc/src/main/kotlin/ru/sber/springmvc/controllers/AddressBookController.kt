package ru.sber.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record

@Controller
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

    @PostMapping("/app/add")
    fun addRecord(@ModelAttribute("record") record: Record): String{
        addressBook.addRecord(3, record )//TODO добавить метод по генерации ID?
        return "success"
    }

    @GetMapping("/app/list")
    fun showAll(model: Model): String {
        model.addAttribute("records", addressBook.getAll())
        return "showAll"
    }

    @GetMapping("/app/view")
    fun getById(@RequestParam("id") id: Long, model: Model): String? {
        model.addAttribute("recordById", addressBook.getById(id))
        return "getById"
    }
}