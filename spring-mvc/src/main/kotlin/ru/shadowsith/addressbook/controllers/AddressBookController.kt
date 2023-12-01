package ru.shadowsith.addressbook.controllers

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.shadowsith.addressbook.dto.Record
import ru.shadowsith.addressbook.services.AddressBookService


@Controller
@RequestMapping("/app")
class AddressBookController(
    private val addressBookService: AddressBookService
) {

    @PostMapping(path = ["add"])
    fun addRecord(
        @ModelAttribute("record") record: Record,
        model: Model
    ): String? {
        addressBookService.add(record)
        return "redirect:/app/list"
    }

    @GetMapping(path = ["add"])
    fun addRecordView(
        model: Model
    ): String? {
        model.addAttribute("record", Record())
        return "add"
    }

    @GetMapping(path = ["list"])
    fun getRecords(
        @RequestParam name: String?,
        model: Model
    ): String? {
        val records = name?.let { addressBookService.findByName(name) } ?: addressBookService.findAll()
        model.addAttribute("records", records)
        return "list"
    }

    @GetMapping(path = ["{id}/delete"])
    fun deleteRecord(
        @PathVariable id: String
    ): String? {
        addressBookService.delete(id)
        return "redirect:/app/list"
    }

    @GetMapping(path = ["{id}/view"])
    fun getRecord(
        @PathVariable id: String,
        model: Model
    ): String? {
        val record = addressBookService.findByGuid(id)
        model.addAttribute("record", record)
        return "view"
    }

    @PostMapping(
        path = ["/{id}/edit"]
    )
    fun changeRecord(
        @PathVariable id: String,
        @ModelAttribute("record") record: Record,
        model: Model
    ): String? {
        val changeRecord = addressBookService.change(id, record)
        model.addAttribute("record", changeRecord)
        return "view"
    }

}