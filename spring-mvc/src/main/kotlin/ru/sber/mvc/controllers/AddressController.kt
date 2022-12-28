package ru.sber.mvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.mvc.models.AddressRow
import ru.sber.mvc.repositories.AddressBookRepo

@Controller
@RequestMapping("/app")
class AddressController(@Autowired val repository: AddressBookRepo) {

    //  /app/list просмотр записей и поиск если будет переданы query параметры запроса
    @GetMapping("/list")
    fun list(@RequestParam(required = false) name: String?, @RequestParam(required = false) phone: String?, model: Model): String {
        model.addAttribute("rows", repository.getList(name, phone))
        return "list"
    }

    // /app/{id}/view просмотр конкретной записи
    @GetMapping("/{id}/view")
    fun view(model: Model, @PathVariable id: String): String {
        model.addAttribute("row", repository.getById(id.toInt()))
        return "view"
    }

    // /app/{id}/edit редактирование конкретной записи
    @GetMapping("/{id}/edit")
    fun editGet(model: Model, @PathVariable id: String): String {
        model.addAttribute("row", repository.getById(id.toInt()))
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editPost(row: AddressRow, @PathVariable id: String): String {
        repository.update(row)
        return "redirect:/app/list"
    }

    // /app/add добавление записи
    @GetMapping("/add")
    fun add(model: Model) :String{
        model.addAttribute("row", AddressRow())
        return "add"
    }

    @PostMapping("/add")
    fun add(row: AddressRow) :String{
        repository.insert(row)
        return "redirect:/app/list"
    }

    // /app/{id}/delete удаление конкретной записи
    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: String):String {
        repository.delete(id.toInt())
        return "redirect:/app/list"
    }

}