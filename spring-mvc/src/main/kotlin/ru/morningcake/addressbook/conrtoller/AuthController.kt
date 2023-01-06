package ru.morningcake.addressbook.conrtoller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import ru.morningcake.addressbook.service.UserService
import ru.morningcake.addressbook.utils.AppUtils
import javax.servlet.http.HttpServletRequest

@Controller
class AuthController @Autowired constructor(private val appUtils : AppUtils) {

    @GetMapping("/login")
    fun loginForm(model: Model): String {
        appUtils.addBaseUrlToModel(model)
        return "login"
    }

    @PostMapping("/logout")
    fun logout(model: Model, request: HttpServletRequest): String {
        appUtils.addBaseUrlToModel(model)
        request.session.invalidate()
        return "login"
    }

}