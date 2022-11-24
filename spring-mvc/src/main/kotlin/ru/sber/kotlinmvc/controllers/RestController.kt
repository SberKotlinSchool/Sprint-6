package ru.sber.kotlinmvc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sber.kotlinmvc.entities.Client
import ru.sber.kotlinmvc.services.ClientRepository

@Controller
@RequestMapping("/rest")
class RestController @Autowired constructor(val repo : ClientRepository) {

    @GetMapping("/list")
    fun getList(@RequestParam(required = false) name: String?,
                @RequestParam(required = false) address: String?,
                @RequestParam(required = false) phone: String?,
                @RequestParam(required = false) email: String?
    ) : ResponseEntity<Collection<Client>> {
        return ResponseEntity.ok(repo.find(name, address, phone, email))
    }

    @GetMapping("/{id}/view")
    fun getClient(@PathVariable("id") id : Integer) : ResponseEntity<Client> {
        return ResponseEntity.of(repo.findById(id))
    }

    @PostMapping("/add")
    fun addClient(@RequestBody client: Client): ResponseEntity<Client> {
        repo.save(client)
        return ResponseEntity.ok(client)
    }

    @PutMapping("/{id}/edit")
    fun editClient(@RequestBody client: Client, result: BindingResult): ResponseEntity<Client?> {
        repo.save(client)
        return ResponseEntity.ok(client)
    }

    @DeleteMapping("/{id}/delete")
    fun deleteClient(@PathVariable id: Integer) : ResponseEntity<Any> {
        repo.delete(id)
        return ResponseEntity.ok(null)
    }

}

