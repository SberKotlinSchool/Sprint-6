package ru.sber.springmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.sber.springmvc.dto.Entry
import ru.sber.springmvc.services.AppService

@RestController
@RequestMapping("api")
class ApiController @Autowired constructor(private val service: AppService) {

    @PostMapping("/add")
    fun add(@RequestBody entry: Entry) = service.add(entry)

    @GetMapping("/list")
    fun list(@RequestParam(required = false) query: String?): List<Entry> = service.list(query)

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) =
        service.view(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @RequestMapping("{id}/edit", method = [RequestMethod.POST])
    fun edit(@PathVariable id: String, @RequestBody entry: Entry) =
        service.edit(id, entry)

    @RequestMapping("{id}/delete", method = [RequestMethod.DELETE])
    fun delete(@PathVariable id: String) =
        service.delete(id)
}