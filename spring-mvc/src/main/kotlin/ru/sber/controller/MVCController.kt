package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.sber.model.AddressModel
import ru.sber.repository.DB

@Controller
@RequestMapping("/app")
class MVCController {

    @Autowired
    private val repository = DB()

    @PostMapping("/add")
    fun createAddress(@ModelAttribute addressModel: AddressModel, model: Model): String {
        repository.save(addressModel.address)
        model.addAttribute("address", addressModel.address)
        return "success"
    }

    @GetMapping("/list")
    fun getAddresses(@RequestParam(required = false) query: String?, model: Model): String {
        val res = if (query.isNullOrEmpty()) repository.getAll()
        else {
            repository.getAll().filter { it.value == query }
        }
        model.addAttribute("addresses", res)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun getAddress(@PathVariable id: String, model: Model): String {
        val entity = repository.getById(id.toLong())
        model.addAttribute("address", entity)
        return "get"
    }

    @PostMapping("/{id}/edit")
    fun updateAddress(@PathVariable id: String, @ModelAttribute addressModel: AddressModel, model: Model): String {
        repository.saveById(id.toLong(), addressModel.address)
        val res = repository.getAll()
        model.addAttribute("addresses", res)
        return "list"
    }

    @PostMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: String, model: Model): String {
        repository.delete(id.toLong())
        val res = repository.getAll()
        model.addAttribute("addresses", res)
        return "list"
    }
}