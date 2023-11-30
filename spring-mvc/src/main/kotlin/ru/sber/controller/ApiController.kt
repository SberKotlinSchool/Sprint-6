package ru.sber.controller

import org.springframework.web.bind.annotation.*
import ru.sber.database.DbService
import ru.sber.dto.AddressDto
import ru.sber.dto.QueryDto

@RestController
@RequestMapping("/api")
class ApiController(val dbService: DbService) {

    @PostMapping("add")
    fun add(@RequestBody addressDto: AddressDto) {
        dbService.createAddress(addressDto)
    }

    @GetMapping("list")
    fun list(): List<AddressDto> {
        return dbService.findAll()
    }

    @PostMapping("list")
    fun listWithFilter(@RequestBody queryDto: QueryDto): List<AddressDto> {
        return dbService.findByQuery(queryDto)
    }

    @GetMapping("{id}/view")
    fun viewById(@PathVariable id: Long): AddressDto? {
        return dbService.findById(id)
    }

    @PutMapping("{id}/edit")
    fun editById(@PathVariable id: Long, @RequestBody addressDto: AddressDto): Boolean {
        return dbService.edit(id, addressDto)
    }

    @DeleteMapping("{id}/delete")
    fun deleteById(@PathVariable id: Long): Boolean {
        return dbService.delete(id)
    }

}