package io.vorotov.diary.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import io.vorotov.diary.models.DiaryRecord
import io.vorotov.diary.services.DiaryService
import java.time.LocalDate

@RestController
@RequestMapping("api")
class DiaryRestController @Autowired constructor(private val service: DiaryService) {

    @GetMapping("/list")
    fun list(@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) date: LocalDate?,
             @RequestParam(required = false) message: String?) : List<DiaryRecord> {
        return service.list(date, message)
    }

    @PostMapping("/add")
    fun add(@RequestBody client: DiaryRecord) = service.add(client)

    @GetMapping("/{date}/view")
    fun view(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate) =
        service.view(date) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @RequestMapping("{date}/edit", method = [RequestMethod.POST])
    fun edit(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate, @RequestBody record: DiaryRecord) =
        service.edit(date, record)

    @RequestMapping("{date}/delete", method = [RequestMethod.DELETE])
    fun delete(@PathVariable @DateTimeFormat(iso = ISO.DATE) date: LocalDate) =
        service.delete(date)

}