package ru.sber.agadressbook.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.sber.agadressbook.models.Person
import ru.sber.agadressbook.service.AddressBookWebService

@Controller
class AddressBookWebController @Autowired constructor(val addressBookWebService: AddressBookWebService) {



    @GetMapping("/person/{id}")
    fun getPerson(@PathVariable("id") id : Int) {
        println("We are in controller $id")
        addressBookWebService.getPersonById(id)
    }

}