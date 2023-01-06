package ru.sber.app.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class StartController {

    @PostMapping("/auth")
    fun auth(): String {
        return "authorization"
    }

    @GetMapping("/api")
    @ResponseBody
    fun api(): String {
        return "JSON API. Для работы с адресной книгой воспользуйтесь запросами. " +
                "Добавить запись /add. " +
                "Просмотреть список /list. " +
                "Просмотреть запись(id) /id/view. " +
                "Редактировать запись /id/edit. " +
                "Удалить запись /id/delete. " +
                "Параметры: firstName - имя, lastName - фамилия, city - город проживания."
    }
}