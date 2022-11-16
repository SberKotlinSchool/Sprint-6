package ru.sber.ufs.cc.kulinich.springmvc.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel

@Controller
class MVCController {


    @GetMapping("/", "/login")
    fun indexOrLogin(model: Model): String {
        // todo: redirect to "/login"
        return "login"
    }

    @PostMapping("/app/add")
    fun add(@ModelAttribute form: AddressBookModel,model: Model) : String {
        return "add"
    }

    @GetMapping("/app/list")
    fun list(model: Model) : String {
        return "list"
    }

    @GetMapping("/app/{id}/view")
    fun view(@PathVariable("id") id : String, model: Model) : String {
        return "view"
    }

    @GetMapping("/app/{id}/edit")
    fun edit(@PathVariable("id") id : String, model: Model) : String {
        return "edit"
    }

    @PostMapping("/app/{id}/edit")
    fun editProcessing(@PathVariable("id") id : String,
                       @ModelAttribute form: AddressBookModel,
                       model: Model) : String {
        return "view"
    }

    @GetMapping("/app/{id}/delete")
    fun delte(@PathVariable("id") id : String, model: Model) : String {
        return "view"
    }


}