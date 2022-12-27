package ru.sber.mvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.mvc.models.AddressRow
import ru.sber.mvc.repositories.AddressBookRepo

@RestController
@RequestMapping("/api")
class RestApiController (@Autowired val repository: AddressBookRepo) {

    @GetMapping("/list")
    fun list(@RequestParam(required = false) name: String?, @RequestParam(required = false) phone: String?) : ResponseEntity<List<AddressRow>> {
        return ResponseEntity.ok(repository.getList(name, phone))
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id: String) : ResponseEntity<AddressRow> {
        return ResponseEntity.ok(repository.getById(id.toInt()))
    }

    @PostMapping("/add")
    fun add(@RequestBody row: AddressRow) : ResponseEntity<AddressRow> {
        repository.insert(row)
        return ResponseEntity.ok(row)
    }

    @PutMapping(value = ["/{id}/edit"])
    fun edit(@PathVariable id: Int, @RequestBody row: AddressRow, result: BindingResult) : ResponseEntity<AddressRow?> {
        repository.update(row)
        return ResponseEntity.ok(row)
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: String, row: AddressRow)  {
        repository.delete(id.toInt())
    }
}