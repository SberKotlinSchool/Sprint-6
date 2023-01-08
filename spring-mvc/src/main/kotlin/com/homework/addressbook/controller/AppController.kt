package com.homework.addressbook.controller;

import com.homework.addressbook.dto.Record
import com.homework.addressbook.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class AppController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addRecord(@ModelAttribute("record") record: Record): String {
        addressBookService.addRecord(record);
        return "redirect:/app/list";
    }

    @GetMapping("/list")
    fun getRecords(model: Model): String {
        model.addAttribute("records", addressBookService.getRecords());
        model.addAttribute("record", Record());
        return "list";
    }

    @GetMapping("/{id}/view")
    fun getCurrentRecord(@PathVariable id: Int, model: Model): String {
        model.addAttribute("record", addressBookService.getCurrentRecord(id));
        return "view"
    }

    @GetMapping("/{id}/edit")
    fun editRecordView(@PathVariable id: Int, model: Model): String {
        model.addAttribute("record", addressBookService.getCurrentRecord(id));
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editRecordSubmit(@PathVariable id: Int, @ModelAttribute("record") record: Record): String {
        addressBookService.editRecord(id, record)
        return "redirect:/app/list";
    }


    @DeleteMapping("/{id}/delete")
    fun deleteRecord(@PathVariable id: Int): String {
        addressBookService.deleteRecord(id);
        return "redirect:/app/list";
    }
}
