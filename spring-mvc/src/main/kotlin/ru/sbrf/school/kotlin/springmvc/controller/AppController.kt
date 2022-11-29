package ru.sbrf.school.kotlin.springmvc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sbrf.school.kotlin.springmvc.entity.Person
import ru.sbrf.school.kotlin.springmvc.repository.AddressBookRepository

@Controller
@RequestMapping("/app")
class AppController(@Autowired val repository: AddressBookRepository) {

    /**
     * /app/add добавление записи
     */
    @GetMapping("/add")
    fun add(model: Model) :String{
        model.addAttribute("person", Person())
        return "add"
    }
    /**
     * /app/add добавление записи
     */
    @PostMapping("/add")
    fun add(person: Person) :String{
        repository.save(person)
        return "redirect:/app/list"
    }

    /**
     *  /app/list просмотр записей и поиск если будет переданы query параметры запроса
     */
    @GetMapping("/list")
    fun list(model: Model): String {
        model.addAttribute("persons", repository.getAll())
        return "list"
    }

    /**
     * /app/{id}/view просмотр конкретной записи
     */
    @GetMapping("/{id}/view")
    fun view(model: Model, @PathVariable id: String): String {
        model.addAttribute("person", repository.getById(id.toLong()))
        return "view"
    }

    /**
     * /app/{id}/edit редактирование конкретной записи
     */
    @GetMapping("/{id}/edit")
    fun editGet(model: Model, @PathVariable id: String): String {
        model.addAttribute("person", repository.getById(id.toLong()))
        return "edit"
    }

    /**
     * /app/{id}/edit редактирование конкретной записи
     */
    @PostMapping("/{id}/edit")
    fun editPost(person: Person, @PathVariable id: String): String {
        repository.save(person)
        return "redirect:/app/list"
    }

    /**
     * /app/{id}/delete удаление конкретной записи
     */
    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: String):String {
        repository.delete(id.toLong())
        return "redirect:/app/list"
    }
}
