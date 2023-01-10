package io.vorotov.diary.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import io.vorotov.diary.models.DiaryRecord
import io.vorotov.diary.services.DiaryService
import java.time.LocalDate


@Controller
@RequestMapping("app")
class DiaryMvcController @Autowired constructor(val service: DiaryService) {


    @GetMapping("/list")
    fun list(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) date: LocalDate?,
        @RequestParam(required = false) message: String?,
        model: Model
    ): String {
        model.addAttribute("list", service.list(date, message))
        return "list"
    }

    @GetMapping("/add")
    fun add() = "add"

    @PostMapping("/add")
    fun add(@ModelAttribute("record") record: DiaryRecord): String {
        service.add(record)
        return "redirect:/app/list"
    }

    @GetMapping("/{date}/delete")
    fun delete(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate, model: Model): String {
        return service.view(date)?.let {
            "delete"
        } ?: "redirect:/app/list"
    }

    @PostMapping("/{date}/delete")
    fun delete(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate): String {
        service.delete(date)

        return "redirect:/app/list"
    }

    @GetMapping("/{date}/view")
    fun view(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate, model: Model): String {
        model.addAttribute("record", service.view(date))
        return "view"
    }

    @GetMapping("/{date}/edit")
    fun edit(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate, model: Model): String {
        return service.view(date)?.let {
            model.addAttribute("record", it)
            "edit"
        } ?: "redirect:/app/list"
    }

    @PostMapping("/{date}/edit")
    fun edit(
        @ModelAttribute("record") record: DiaryRecord,
        @PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate
    ): String {
        service.edit(date, record)
        return "redirect:/app/list"
    }
}