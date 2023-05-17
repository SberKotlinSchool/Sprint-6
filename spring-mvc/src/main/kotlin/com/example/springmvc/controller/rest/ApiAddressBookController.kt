package com.example.springmvc.controller.rest

import com.example.springmvc.model.Contact
import com.example.springmvc.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ApiAddressBookController {

    @Autowired
    private lateinit var contactRepository: ContactRepository

    @GetMapping("/list")
    fun listAllContacts(model: Model): ResponseEntity<List<Contact>> {
        return ResponseEntity.ok().body(contactRepository.list()) //200
    }

    @GetMapping("/{id}/view")
    fun viewContact(@PathVariable id: Int, model: Model): ResponseEntity<Contact>  {
        return ResponseEntity.ok().body(contactRepository.view(id) //200
            ?: return ResponseEntity.notFound().build()) //404
    }

    @PatchMapping("/{id}/edit")
    fun editContact(
        @Valid @RequestBody editedContact: Contact,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {

        return if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().build() //400
        } else if (!contactRepository.edit(editedContact)) {
            ResponseEntity.notFound().build() //404
        } else {
            ResponseEntity.accepted().build() //202
        }
    }

    @DeleteMapping("/{id}/delete")
    fun deleteContact(@PathVariable id: Int): ResponseEntity<Unit> {
        return if (contactRepository.delete(id)) {
            ResponseEntity.accepted().build() //202
        } else {
            ResponseEntity.notFound().build() //404
        }


    }

    @PostMapping("/add")
    fun addContact(
        @Valid @RequestBody contact: Contact,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build()
        }
        contactRepository.add(contact)
        return ResponseEntity.accepted().build()
    }
}