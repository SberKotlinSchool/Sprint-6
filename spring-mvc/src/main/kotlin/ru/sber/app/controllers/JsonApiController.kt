package ru.sber.app.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.app.AddressBook
import ru.sber.app.FormModel

@RestController
@RequestMapping(path=["/api/"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE])
class JsonApiController {
    @Autowired
    private lateinit var addressBook : AddressBook

    @PostMapping("add")
    fun add(@RequestBody form: FormModel, @CookieValue(name = "auth") cookie: String) {
        if ((form.firstName != null || form.lastName != null || form.city != null) && cookie.isNotEmpty()) {
            addressBook.addValue(FormModel(form.firstName, form.lastName, form.city))
        }
    }

    @GetMapping("list")
    fun list(): Collection<FormModel> {
        return addressBook.getFields().values
    }

    @GetMapping("{id}/view")
    fun view(@PathVariable ("id") id: Int): FormModel {
        return addressBook.getValue(id)
    }

    @PutMapping("{id}/edit")
    fun edit(@PathVariable("id") id: Int, @RequestBody form: FormModel, model: Model, @CookieValue(name = "auth") cookie: String) {
        if ((form.firstName != null || form.lastName != null || form.city != null) && cookie.isNotEmpty()) {
            addressBook.addValue(id, FormModel(form.firstName, form.lastName, form.city))
        }
    }

    @DeleteMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Int, @CookieValue(name = "auth") cookie: String) {
        if(cookie.isNotEmpty()) addressBook.deleteValue(id)
    }
}