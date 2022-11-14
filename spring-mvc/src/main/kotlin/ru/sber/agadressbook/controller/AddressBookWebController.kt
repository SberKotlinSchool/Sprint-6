package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService

@Controller
class AddressBookWebController @Autowired constructor(val addressBookWebService: AddressBookWebService) {

    @GetMapping("/person/{id}")
    fun getPerson(@PathVariable("id") id : Int, model : Model) : String {
        addressBookWebService.getPersonById(id)?.firstName?.let { model["firstName"] = it }
        model["firstName"] = addressBookWebService.getPersonById(id)?.firstName!!
        model["title"] = "This is a address-book title"
        return "test"
    }

}