package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import ru.sber.model.BaseEntity
import ru.sber.service.AddressBookService

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addBaseEntity(@RequestBody baseEntity: BaseEntity): ResponseEntity<Long> {
        return ResponseEntity.ok(addressBookService.addBaseEntity(baseEntity))
    }

    @GetMapping("/list")
    fun getBaseEntities(@RequestParam(required = false) query: String?): ResponseEntity<Set<Map.Entry<Long, BaseEntity>>> {
        return ResponseEntity.ok(addressBookService.getBaseEntities(query))
    }

    @GetMapping("{id}/view")
    fun viewBaseEntity(@PathVariable id: Long): ResponseEntity<BaseEntity> {
        return ResponseEntity.ok().body(
            addressBookService.findBaseEntityById(id)
                ?: return ResponseEntity.notFound().build()
        )
    }

    @PutMapping("{id}/edit")
    fun editBaseEntity(@PathVariable id: Long, @RequestBody baseEntity: BaseEntity): ResponseEntity<BaseEntity> {
        return ResponseEntity.ok().body(
            addressBookService.editBaseEntity(id, baseEntity)
                ?: return ResponseEntity.notFound().build()
        )
    }

    @DeleteMapping("/{id}/delete")
    fun deleteBaseEntity(@PathVariable id: Long): ResponseEntity<BaseEntity> {
        return ResponseEntity.ok().body(
            addressBookService.deleteBaseEntity(id)
                ?: return ResponseEntity.notFound().build()
        )
    }
}