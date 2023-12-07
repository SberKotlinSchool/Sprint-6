package addressbook.controller

import addressbook.model.Person
import addressbook.service.AddressBookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-controller/app")
class RestControllers(private val addressBookService: AddressBookService) {

    @GetMapping("/list")
    fun getPersons(@RequestParam(required = false) firstName: String?): ResponseEntity<List<Person>> {
        println("rest controller list")
        return firstName?.let {
            ResponseEntity.ok(addressBookService.getPersonByFirstName(it))
        } ?: run {
            ResponseEntity.ok(addressBookService.getAllPersons())
        }
    }

    @PostMapping("/add")
    fun addPerson(@RequestBody person: Person): ResponseEntity<Int> {
        return ResponseEntity.ok(addressBookService.addPerson(person))
    }

    @GetMapping("{id}/view")
    fun viewPerson(@PathVariable id: Int): ResponseEntity<Person> {
        return ResponseEntity.ok().body(
            addressBookService.getPersonById(id)
                ?: return ResponseEntity.notFound().build()
        )
    }

    @PutMapping("{id}/edit")
    fun editPerson(@PathVariable id: Int, @RequestBody entry: Person) {
        return addressBookService.editPerson(id, entry)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePerson(@PathVariable id: Int) {
        return addressBookService.deletePersonById(id)
    }
}