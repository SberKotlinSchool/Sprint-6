package com.sbuniver.homework

import com.sbuniver.homework.dto.AddressBook
import com.sbuniver.homework.dto.AddressDto
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/app")
class AppController {

    @Autowired
    lateinit var addressBook: AddressBook

    @GetMapping("/list")
    fun list(model: Model): String {
        logger.info { "income get request to /app/list" }
        model.addAttribute("addresses", addressBook.addresses)
        return "list"
    }

    @GetMapping("/{id}/view")
    fun view(model: Model, @PathVariable id: Int): String {
        model.addAttribute(
            "address",
            if (addressBook.get(id) == null) "No address found with id=$id"
            else addressBook.get(id).toString()
        )
        return "viewOne"
    }

    @PostMapping("/{id}/edit")
    fun editDo(
        @PathVariable id: Int,
        @RequestBody formData: MultiValueMap<String, String>,
        response: HttpServletResponse
    ) {
        println(formData)
        val addressDto = AddressDto(
            id,
            formData["name"]!!.joinToString(separator = " "),
            formData["city"]!!.joinToString(separator = " "),
            formData["street"]!!.joinToString(separator = " "),
            formData["home"]!![0].toInt(),
        )
        addressBook.update(addressDto)
        response.sendRedirect("/app/list")
    }

    @PostMapping("/add")
    fun add(
        @RequestBody formData: MultiValueMap<String, String>,
        response: HttpServletResponse
    ) {
        addressBook.add(
            AddressDto(
                addressBook.maxId() + 1,
                formData["name"]!!.joinToString(separator = " "),
                formData["city"]!!.joinToString(separator = " "),
                formData["street"]!!.joinToString(separator = " "),
                formData["home"]!![0].toInt(),
            )
        )
        response.sendRedirect("/app/list")
    }

    @GetMapping("/{id}/edit")
    fun editPage(model: Model, @PathVariable id: Int): String {
        model.addAttribute("address", addressBook.get(id))
        return "edit"
    }

    @DeleteMapping("/{id}/delete")
    fun deleteDo(
        @PathVariable id: Int,
        response: HttpServletResponse
    ) {
        addressBook.delete(addressBook.get(id)!!)
        response.sendRedirect("/app/list")
    }

    @GetMapping("/{id}/delete")
    fun deletePage(model: Model, @PathVariable id: Int): String {
        model.addAttribute("address", addressBook.get(id))
        return "delete"
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}