package ru.sber.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import ru.sber.model.AddressModel
import ru.sber.repository.DB

@RestController
@RequestMapping("/api")
class RestController {

    @Autowired
    private val repository = DB()

    @PostMapping("/add")
    fun createAddress(@RequestBody addressModel: AddressModel) = ResponseEntity.ok(
        repository.save(addressModel.address)
    )

    @GetMapping("/list")
    fun getAddresses(@RequestParam(required = false) query: String?): ResponseEntity<Map<Long, String>> {
        val res = if (query.isNullOrEmpty()) repository.getAll()
        else {
            repository.getAll().filter { it.value == query }
        }
        return ResponseEntity.ok(res)
    }

    @GetMapping("/{id}/view")
    fun getAddress(@PathVariable id: String): ResponseEntity<AddressModel> {
        val res = repository.getById(id.toLong())
        if (res.isNullOrEmpty()) {
            return ResponseEntity<AddressModel>(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(AddressModel(res))
    }

    @PutMapping("/{id}/edit")
    fun updateAddress(@PathVariable id: String, @RequestBody addressModel: AddressModel): ResponseEntity<HttpStatus> {
        repository.saveById(id.toLong(), addressModel.address)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("{id}/delete")
    fun deleteAddress(@PathVariable id: String): ResponseEntity<HttpStatus> {
        repository.delete(id.toLong())
        return ResponseEntity(HttpStatus.OK)
    }
}