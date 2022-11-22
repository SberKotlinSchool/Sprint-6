package ru.sber.ufs.cc.kulinich.springmvc.controllers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.sber.ufs.cc.kulinich.springmvc.filters.LoggingFilter
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel
import ru.sber.ufs.cc.kulinich.springmvc.services.AddressBookService

@Controller
@RequestMapping("/app/*")
class MVCController {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    lateinit var addrBook : AddressBookService
        @Autowired set

    @GetMapping("/add")
    fun add(model: Model) : String {
        return "add"
    }


    @PostMapping("/add")
    fun add(@ModelAttribute form: AddressBookModel,model: Model) : String {
        addrBook.add(form)
        logger.info("${form.id}\t${form.name}\t${form.phone}")
        model.addAttribute("records", addrBook.getAll())
        return "redirect:/app/list"
    }

    @RequestMapping("/list")
    fun list(model: Model) : String {
        model.addAttribute("records", addrBook.getAll())
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable("id") id : String, model: Model) : String {
        model.addAttribute("record", addrBook.getById(id.toInt()))
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id : String, model: Model) : String {
        model.addAttribute("record", addrBook.getById(id.toInt()))

        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editProcessing(@PathVariable("id") id : String,
                       @ModelAttribute form: AddressBookModel,
                       model: Model) : String {
        addrBook.edit(id.toInt(), form)
        model.addAttribute("record", addrBook.getById(id.toInt()))
        return "redirect:/app/${id.toInt()}/view"
    }

    @GetMapping("/{id}/delete")
    fun delte(@PathVariable("id") id : String, model: Model) : String {
        addrBook.delete(id.toInt())
        return "redirect:/app/list"
    }
}