package addressbook.controller

import addressbook.model.Person
import addressbook.service.AddressBookService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class Controllers(private val addressBookService: AddressBookService) {
    @GetMapping("/list")
    fun viewHomePage(@RequestParam(required = false) firstName: String?, model: Model): String {

        firstName?.let {
            model.addAttribute("addressBook", addressBookService.getPersonByFirstName(it))
        } ?: run {
            model.addAttribute("addressBook", addressBookService.getAllPersons())
        }

        return "list"
    }

    @GetMapping("/showNewPersonForm")
    fun showNewPersonForm(model: Model): String {
        val person = Person()
        model.addAttribute("person", person)
        return "new_person"
    }

    @PostMapping("/add")
    fun addPerson(@ModelAttribute("person") person: Person): String {
        addressBookService.addPerson(person)
        return "redirect:/app/list"
    }

    @GetMapping("/showFormForUpdate/{id}")
    fun showFormForUpdate(@PathVariable(value = "id") id: Int, model: Model): String {

        val person: Person? = addressBookService.getPersonById(id)

        model.addAttribute("person", person)
        model.addAttribute("id", id)
        return "update_person"
    }

    @PostMapping("/edit")
    fun editPerson(@ModelAttribute("person") person: Person, @RequestParam("additionalField") id: String): String {
        addressBookService.editPerson(id.toInt(), person)
        return "redirect:/app/list"
    }

    @GetMapping("/showFormView/{id}")
    fun showFormView(@PathVariable(value = "id") id: Int, model: Model): String {

        val person: Person? = addressBookService.getPersonById(id)

        model.addAttribute("person", person)
        model.addAttribute("id", id)
        return "view_person"
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    fun deletePerson(@PathVariable(value = "id") id: Int, model: Model): String {
        addressBookService.deletePersonById(id)
        return "redirect:/app/list"
    }
}