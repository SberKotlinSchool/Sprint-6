package ru.sber.controller

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.database.DbService
import ru.sber.dto.AddressDto
import ru.sber.dto.QueryDto

@Controller
@RequestMapping("/app")
class MvcController(val dbService: DbService) {

    companion object : KLogging()

    /**
     * Обработчик страницы list.html - просмотр записей
     */
    @GetMapping("/list")
    fun getAddressList(model: Model): String {
        model.addAttribute("addresses", dbService.findAll())
        return "list"
    }

    /**
     * Обработчик страницы list.html - просмотр отфильтрованных записей
     */
    @PostMapping("/list")
    fun getAddressListWithFilter(@ModelAttribute query: QueryDto, model: Model): String {
        model.addAttribute("addresses", dbService.findByQuery(query))
        return "list"
    }

    /**
     * Обработчик добавления новых записей через форму на странице list.html
     */
    @PostMapping("/add")
    fun createAddress(@ModelAttribute addressDto: AddressDto, model: Model): String {
        dbService.createAddress(addressDto)
        model.addAttribute("addresses", dbService.findAll())
        return "list"
    }

    /**
     * Обработчик действия просмотра записи по id.
     */
    @GetMapping("/{id}/view")
    fun getAddress(@PathVariable id: Long, model: Model): String {
        val address = dbService.findById(id)
        model.addAttribute("addresses", listOf(address))
        return "list"
    }

    /**
     * Обработчик обновления записей по id.
     */
    @PostMapping("/{id}/edit")
    fun updateAddress(@PathVariable id: Long, @ModelAttribute addressDto: AddressDto, model: Model): String {
        dbService.edit(id, addressDto)
        model.addAttribute("addresses", dbService.findAll())
        return "list"
    }

    /**
     * Обработчик удаления записей.
     * html-форма умеет только POST-метод, поэтому не http-delete.
     */
    @PostMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: Long, model: Model): String {
        if (!dbService.delete(id)) {
            logger.warn("delete - not ok")
        }

        model.addAttribute("addresses", dbService.findAll())
        return "list"
    }
}