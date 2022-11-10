package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.model.Customer
import ru.sber.services.AddressBookService
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/app"])
class AddressBookAppController @Autowired constructor(val service: AddressBookService) {

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun addCustomer(model: Model): String {
        model.addAttribute("customer", Customer())
        return "add"
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addCustomer(@Valid @ModelAttribute("customer") customer: Customer, result: BindingResult): String {
        if (result.hasErrors()) {
            return "add"
        }
        service.add(customer)
        return "redirect:/app/list"
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun getCustomers(@RequestParam(required = false) fio: String?, model: Model): String {
        model.addAttribute("customers", service.getAll(fio))
        return "list"
    }

    @RequestMapping(value = ["/{id}/view"], method = [RequestMethod.GET])
    fun viewCustomer(@PathVariable id: Int, model: Model): String {
        model.addAttribute("customer", service.getById(id))
        return "view"
    }

    @RequestMapping(value = ["/{id}/edit"], method = [RequestMethod.GET])
    fun editClient(@PathVariable id: Int, model: Model): String {
        model.addAttribute("customer", service.getById(id))
        return "edit"
    }

    @RequestMapping(value = ["/{id}/edit"], method = [RequestMethod.POST])
    fun editClient(
        @PathVariable id: Int,
        @Valid @ModelAttribute("customer") client: Customer,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "edit"
        }
        service.update(client)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deleteCustomer(@PathVariable id: Int, model: Model): String {
        service.delete(id)
        return "redirect:/app/list"
    }

}