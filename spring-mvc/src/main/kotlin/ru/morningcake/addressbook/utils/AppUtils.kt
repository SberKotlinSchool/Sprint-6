package ru.morningcake.addressbook.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue

@Component
class AppUtils {

    @Value("\${app.baseUrl}")
    lateinit var baseUrl : String

    fun addBaseUrlToModel(model : Model) : Model {
        model.addAttribute("baseUrl", baseUrl)
        return model
    }

    fun addBaseUrlAndUserNameToModel(model : Model, userName : String) : Model {
        model.addAttribute("baseUrl", baseUrl)
        model.addAttribute("userName", userName)
        return model
    }
}